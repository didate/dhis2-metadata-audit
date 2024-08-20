import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataelement, NewDataelement } from '../dataelement.model';

export type PartialUpdateDataelement = Partial<IDataelement> & Pick<IDataelement, 'id'>;

type RestOf<T extends IDataelement | NewDataelement> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestDataelement = RestOf<IDataelement>;

export type NewRestDataelement = RestOf<NewDataelement>;

export type PartialUpdateRestDataelement = RestOf<PartialUpdateDataelement>;

export type EntityResponseType = HttpResponse<IDataelement>;
export type EntityArrayResponseType = HttpResponse<IDataelement[]>;

@Injectable({ providedIn: 'root' })
export class DataelementService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dataelements');

  create(dataelement: NewDataelement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataelement);
    return this.http
      .post<RestDataelement>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dataelement: IDataelement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataelement);
    return this.http
      .put<RestDataelement>(`${this.resourceUrl}/${this.getDataelementIdentifier(dataelement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dataelement: PartialUpdateDataelement): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataelement);
    return this.http
      .patch<RestDataelement>(`${this.resourceUrl}/${this.getDataelementIdentifier(dataelement)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestDataelement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDataelement[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDataelementIdentifier(dataelement: Pick<IDataelement, 'id'>): string {
    return dataelement.id;
  }

  compareDataelement(o1: Pick<IDataelement, 'id'> | null, o2: Pick<IDataelement, 'id'> | null): boolean {
    return o1 && o2 ? this.getDataelementIdentifier(o1) === this.getDataelementIdentifier(o2) : o1 === o2;
  }

  addDataelementToCollectionIfMissing<Type extends Pick<IDataelement, 'id'>>(
    dataelementCollection: Type[],
    ...dataelementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dataelements: Type[] = dataelementsToCheck.filter(isPresent);
    if (dataelements.length > 0) {
      const dataelementCollectionIdentifiers = dataelementCollection.map(dataelementItem => this.getDataelementIdentifier(dataelementItem));
      const dataelementsToAdd = dataelements.filter(dataelementItem => {
        const dataelementIdentifier = this.getDataelementIdentifier(dataelementItem);
        if (dataelementCollectionIdentifiers.includes(dataelementIdentifier)) {
          return false;
        }
        dataelementCollectionIdentifiers.push(dataelementIdentifier);
        return true;
      });
      return [...dataelementsToAdd, ...dataelementCollection];
    }
    return dataelementCollection;
  }

  protected convertDateFromClient<T extends IDataelement | NewDataelement | PartialUpdateDataelement>(dataelement: T): RestOf<T> {
    return {
      ...dataelement,
      created: dataelement.created?.toJSON() ?? null,
      lastUpdated: dataelement.lastUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDataelement: RestDataelement): IDataelement {
    return {
      ...restDataelement,
      created: restDataelement.created ? dayjs.unix(Number(restDataelement.created)) : undefined,
      lastUpdated: restDataelement.lastUpdated ? dayjs.unix(Number(restDataelement.lastUpdated)) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDataelement>): HttpResponse<IDataelement> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDataelement[]>): HttpResponse<IDataelement[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
