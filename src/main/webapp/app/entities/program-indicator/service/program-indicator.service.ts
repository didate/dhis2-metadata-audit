import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramIndicator, NewProgramIndicator } from '../program-indicator.model';

export type PartialUpdateProgramIndicator = Partial<IProgramIndicator> & Pick<IProgramIndicator, 'id'>;

type RestOf<T extends IProgramIndicator | NewProgramIndicator> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestProgramIndicator = RestOf<IProgramIndicator>;

export type NewRestProgramIndicator = RestOf<NewProgramIndicator>;

export type PartialUpdateRestProgramIndicator = RestOf<PartialUpdateProgramIndicator>;

export type EntityResponseType = HttpResponse<IProgramIndicator>;
export type EntityArrayResponseType = HttpResponse<IProgramIndicator[]>;

@Injectable({ providedIn: 'root' })
export class ProgramIndicatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/program-indicators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(programIndicator: NewProgramIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programIndicator);
    return this.http
      .post<RestProgramIndicator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(programIndicator: IProgramIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programIndicator);
    return this.http
      .put<RestProgramIndicator>(`${this.resourceUrl}/${this.getProgramIndicatorIdentifier(programIndicator)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(programIndicator: PartialUpdateProgramIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programIndicator);
    return this.http
      .patch<RestProgramIndicator>(`${this.resourceUrl}/${this.getProgramIndicatorIdentifier(programIndicator)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProgramIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProgramIndicator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProgramIndicatorIdentifier(programIndicator: Pick<IProgramIndicator, 'id'>): string {
    return programIndicator.id;
  }

  compareProgramIndicator(o1: Pick<IProgramIndicator, 'id'> | null, o2: Pick<IProgramIndicator, 'id'> | null): boolean {
    return o1 && o2 ? this.getProgramIndicatorIdentifier(o1) === this.getProgramIndicatorIdentifier(o2) : o1 === o2;
  }

  addProgramIndicatorToCollectionIfMissing<Type extends Pick<IProgramIndicator, 'id'>>(
    programIndicatorCollection: Type[],
    ...programIndicatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const programIndicators: Type[] = programIndicatorsToCheck.filter(isPresent);
    if (programIndicators.length > 0) {
      const programIndicatorCollectionIdentifiers = programIndicatorCollection.map(
        programIndicatorItem => this.getProgramIndicatorIdentifier(programIndicatorItem)!
      );
      const programIndicatorsToAdd = programIndicators.filter(programIndicatorItem => {
        const programIndicatorIdentifier = this.getProgramIndicatorIdentifier(programIndicatorItem);
        if (programIndicatorCollectionIdentifiers.includes(programIndicatorIdentifier)) {
          return false;
        }
        programIndicatorCollectionIdentifiers.push(programIndicatorIdentifier);
        return true;
      });
      return [...programIndicatorsToAdd, ...programIndicatorCollection];
    }
    return programIndicatorCollection;
  }

  protected convertDateFromClient<T extends IProgramIndicator | NewProgramIndicator | PartialUpdateProgramIndicator>(
    programIndicator: T
  ): RestOf<T> {
    return {
      ...programIndicator,
      created: programIndicator.created?.toJSON() ?? null,
      lastUpdated: programIndicator.lastUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProgramIndicator: RestProgramIndicator): IProgramIndicator {
    return {
      ...restProgramIndicator,
      created: restProgramIndicator.created ? dayjs.unix(restProgramIndicator.created as unknown as number) : undefined,
      lastUpdated: restProgramIndicator.lastUpdated ? dayjs.unix(restProgramIndicator.lastUpdated as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProgramIndicator>): HttpResponse<IProgramIndicator> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProgramIndicator[]>): HttpResponse<IProgramIndicator[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
