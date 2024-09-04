import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { isPresent } from 'app/core/util/operators';
import dayjs from 'dayjs/esm';
import { IProgram, NewProgram } from '../program.model';

type RestOf<T extends IProgram | NewProgram> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type PartialUpdateProgram = Partial<IProgram> & Pick<IProgram, 'id'>;
export type RestProgram = RestOf<IProgram>;
export type EntityResponseType = HttpResponse<IProgram>;
export type EntityArrayResponseType = HttpResponse<IProgram[]>;

@Injectable({ providedIn: 'root' })
export class ProgramService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/programs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProgram>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProgram[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestProgram[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http.get<IProgram[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' });
  }

  getProgramIdentifier(program: Pick<IProgram, 'id'>): string {
    return program.id;
  }

  compareProgram(o1: Pick<IProgram, 'id'> | null, o2: Pick<IProgram, 'id'> | null): boolean {
    return o1 && o2 ? this.getProgramIdentifier(o1) === this.getProgramIdentifier(o2) : o1 === o2;
  }

  addProgramToCollectionIfMissing<Type extends Pick<IProgram, 'id'>>(
    programCollection: Type[],
    ...programsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const programs: Type[] = programsToCheck.filter(isPresent);
    if (programs.length > 0) {
      const programCollectionIdentifiers = programCollection.map(programItem => this.getProgramIdentifier(programItem)!);
      const programsToAdd = programs.filter(programItem => {
        const programIdentifier = this.getProgramIdentifier(programItem);
        if (programCollectionIdentifiers.includes(programIdentifier)) {
          return false;
        }
        programCollectionIdentifiers.push(programIdentifier);
        return true;
      });
      return [...programsToAdd, ...programCollection];
    }
    return programCollection;
  }

  protected convertDateFromServer(restProgram: RestProgram): IProgram {
    return {
      ...restProgram,
      created: restProgram.created ? dayjs.unix(restProgram.created as unknown as number) : undefined,
      lastUpdated: restProgram.lastUpdated ? dayjs.unix(restProgram.lastUpdated as unknown as number) : undefined,
    };
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProgram[]>): HttpResponse<IProgram[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
