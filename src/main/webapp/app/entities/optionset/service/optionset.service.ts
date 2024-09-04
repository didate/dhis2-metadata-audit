import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOptionset, NewOptionset } from '../optionset.model';
import dayjs from 'dayjs/esm';

type RestOf<T extends IOptionset | NewOptionset> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestOptionset = RestOf<IOptionset>;

export type PartialUpdateOptionset = Partial<IOptionset> & Pick<IOptionset, 'id'>;

export type EntityResponseType = HttpResponse<IOptionset>;
export type EntityArrayResponseType = HttpResponse<IOptionset[]>;

@Injectable({ providedIn: 'root' })
export class OptionsetService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/optionsets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(optionset: NewOptionset): Observable<EntityResponseType> {
    return this.http.post<IOptionset>(this.resourceUrl, optionset, { observe: 'response' });
  }

  update(optionset: IOptionset): Observable<EntityResponseType> {
    return this.http.put<IOptionset>(`${this.resourceUrl}/${this.getOptionsetIdentifier(optionset)}`, optionset, { observe: 'response' });
  }

  partialUpdate(optionset: PartialUpdateOptionset): Observable<EntityResponseType> {
    return this.http.patch<IOptionset>(`${this.resourceUrl}/${this.getOptionsetIdentifier(optionset)}`, optionset, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IOptionset>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOptionset[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestOptionset[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestOptionset[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOptionsetIdentifier(optionset: Pick<IOptionset, 'id'>): string {
    return optionset.id;
  }

  compareOptionset(o1: Pick<IOptionset, 'id'> | null, o2: Pick<IOptionset, 'id'> | null): boolean {
    return o1 && o2 ? this.getOptionsetIdentifier(o1) === this.getOptionsetIdentifier(o2) : o1 === o2;
  }

  addOptionsetToCollectionIfMissing<Type extends Pick<IOptionset, 'id'>>(
    optionsetCollection: Type[],
    ...optionsetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const optionsets: Type[] = optionsetsToCheck.filter(isPresent);
    if (optionsets.length > 0) {
      const optionsetCollectionIdentifiers = optionsetCollection.map(optionsetItem => this.getOptionsetIdentifier(optionsetItem)!);
      const optionsetsToAdd = optionsets.filter(optionsetItem => {
        const optionsetIdentifier = this.getOptionsetIdentifier(optionsetItem);
        if (optionsetCollectionIdentifiers.includes(optionsetIdentifier)) {
          return false;
        }
        optionsetCollectionIdentifiers.push(optionsetIdentifier);
        return true;
      });
      return [...optionsetsToAdd, ...optionsetCollection];
    }
    return optionsetCollection;
  }

  protected convertDateFromServer(restOptionset: RestOptionset): IOptionset {
    return {
      ...restOptionset,
      created: restOptionset.created ? dayjs.unix(restOptionset.created as unknown as number) : undefined,
      lastUpdated: restOptionset.lastUpdated ? dayjs.unix(restOptionset.lastUpdated as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOptionset>): HttpResponse<IOptionset> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOptionset[]>): HttpResponse<IOptionset[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
