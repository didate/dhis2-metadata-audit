import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIndicator, NewIndicator } from '../indicator.model';

export type PartialUpdateIndicator = Partial<IIndicator> & Pick<IIndicator, 'id'>;

type RestOf<T extends IIndicator | NewIndicator> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestIndicator = RestOf<IIndicator>;

export type NewRestIndicator = RestOf<NewIndicator>;

export type PartialUpdateRestIndicator = RestOf<PartialUpdateIndicator>;

export type EntityResponseType = HttpResponse<IIndicator>;
export type EntityArrayResponseType = HttpResponse<IIndicator[]>;

@Injectable({ providedIn: 'root' })
export class IndicatorService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/indicators');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(indicator: NewIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indicator);
    return this.http
      .post<RestIndicator>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(indicator: IIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indicator);
    return this.http
      .put<RestIndicator>(`${this.resourceUrl}/${this.getIndicatorIdentifier(indicator)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(indicator: PartialUpdateIndicator): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(indicator);
    return this.http
      .patch<RestIndicator>(`${this.resourceUrl}/${this.getIndicatorIdentifier(indicator)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestIndicator>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestIndicator[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestIndicator[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestIndicator[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIndicatorIdentifier(indicator: Pick<IIndicator, 'id'>): string {
    return indicator.id;
  }

  compareIndicator(o1: Pick<IIndicator, 'id'> | null, o2: Pick<IIndicator, 'id'> | null): boolean {
    return o1 && o2 ? this.getIndicatorIdentifier(o1) === this.getIndicatorIdentifier(o2) : o1 === o2;
  }

  addIndicatorToCollectionIfMissing<Type extends Pick<IIndicator, 'id'>>(
    indicatorCollection: Type[],
    ...indicatorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const indicators: Type[] = indicatorsToCheck.filter(isPresent);
    if (indicators.length > 0) {
      const indicatorCollectionIdentifiers = indicatorCollection.map(indicatorItem => this.getIndicatorIdentifier(indicatorItem)!);
      const indicatorsToAdd = indicators.filter(indicatorItem => {
        const indicatorIdentifier = this.getIndicatorIdentifier(indicatorItem);
        if (indicatorCollectionIdentifiers.includes(indicatorIdentifier)) {
          return false;
        }
        indicatorCollectionIdentifiers.push(indicatorIdentifier);
        return true;
      });
      return [...indicatorsToAdd, ...indicatorCollection];
    }
    return indicatorCollection;
  }

  protected convertDateFromClient<T extends IIndicator | NewIndicator | PartialUpdateIndicator>(indicator: T): RestOf<T> {
    return {
      ...indicator,
      created: indicator.created?.toJSON() ?? null,
      lastUpdated: indicator.lastUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restIndicator: RestIndicator): IIndicator {
    return {
      ...restIndicator,
      created: restIndicator.created ? dayjs(restIndicator.created) : undefined,
      lastUpdated: restIndicator.lastUpdated ? dayjs(restIndicator.lastUpdated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestIndicator>): HttpResponse<IIndicator> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestIndicator[]>): HttpResponse<IIndicator[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
