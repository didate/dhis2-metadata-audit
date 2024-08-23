import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIndicatortype, NewIndicatortype } from '../indicatortype.model';

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
    return this.http.get<IIndicatortype[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
