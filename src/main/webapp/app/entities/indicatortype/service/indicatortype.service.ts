import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIndicatortype, NewIndicatortype } from '../indicatortype.model';
import dayjs from 'dayjs/esm';

type RestOf<T extends IIndicatortype | NewIndicatortype> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestIndicatortype = RestOf<IIndicatortype>;

export type PartialUpdateIndicatortype = Partial<IIndicatortype> & Pick<IIndicatortype, 'id'>;

export type EntityResponseType = HttpResponse<IIndicatortype>;
export type EntityArrayResponseType = HttpResponse<IIndicatortype[]>;

@Injectable({ providedIn: 'root' })
export class IndicatortypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/indicatortypes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(indicatortype: NewIndicatortype): Observable<EntityResponseType> {
    return this.http.post<IIndicatortype>(this.resourceUrl, indicatortype, { observe: 'response' });
  }

  update(indicatortype: IIndicatortype): Observable<EntityResponseType> {
    return this.http.put<IIndicatortype>(`${this.resourceUrl}/${this.getIndicatortypeIdentifier(indicatortype)}`, indicatortype, {
      observe: 'response',
    });
  }

  partialUpdate(indicatortype: PartialUpdateIndicatortype): Observable<EntityResponseType> {
    return this.http.patch<IIndicatortype>(`${this.resourceUrl}/${this.getIndicatortypeIdentifier(indicatortype)}`, indicatortype, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IIndicatortype>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestIndicatortype[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestIndicatortype[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestIndicatortype[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIndicatortypeIdentifier(indicatortype: Pick<IIndicatortype, 'id'>): string {
    return indicatortype.id;
  }

  compareIndicatortype(o1: Pick<IIndicatortype, 'id'> | null, o2: Pick<IIndicatortype, 'id'> | null): boolean {
    return o1 && o2 ? this.getIndicatortypeIdentifier(o1) === this.getIndicatortypeIdentifier(o2) : o1 === o2;
  }

  addIndicatortypeToCollectionIfMissing<Type extends Pick<IIndicatortype, 'id'>>(
    indicatortypeCollection: Type[],
    ...indicatortypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const indicatortypes: Type[] = indicatortypesToCheck.filter(isPresent);
    if (indicatortypes.length > 0) {
      const indicatortypeCollectionIdentifiers = indicatortypeCollection.map(
        indicatortypeItem => this.getIndicatortypeIdentifier(indicatortypeItem)!
      );
      const indicatortypesToAdd = indicatortypes.filter(indicatortypeItem => {
        const indicatortypeIdentifier = this.getIndicatortypeIdentifier(indicatortypeItem);
        if (indicatortypeCollectionIdentifiers.includes(indicatortypeIdentifier)) {
          return false;
        }
        indicatortypeCollectionIdentifiers.push(indicatortypeIdentifier);
        return true;
      });
      return [...indicatortypesToAdd, ...indicatortypeCollection];
    }
    return indicatortypeCollection;
  }

  protected convertDateFromServer(restOptionset: RestIndicatortype): IIndicatortype {
    return {
      ...restOptionset,
      created: restOptionset.created ? dayjs.unix(restOptionset.created as unknown as number) : undefined,
      lastUpdated: restOptionset.lastUpdated ? dayjs.unix(restOptionset.lastUpdated as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestIndicatortype>): HttpResponse<IIndicatortype> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestIndicatortype[]>): HttpResponse<IIndicatortype[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
