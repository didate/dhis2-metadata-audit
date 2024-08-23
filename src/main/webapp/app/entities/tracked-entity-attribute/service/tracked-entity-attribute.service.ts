import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrackedEntityAttribute, NewTrackedEntityAttribute } from '../tracked-entity-attribute.model';

export type PartialUpdateTrackedEntityAttribute = Partial<ITrackedEntityAttribute> & Pick<ITrackedEntityAttribute, 'id'>;

type RestOf<T extends ITrackedEntityAttribute | NewTrackedEntityAttribute> = Omit<T, 'lastUpdated' | 'created'> & {
  lastUpdated?: string | null;
  created?: string | null;
};

export type RestTrackedEntityAttribute = RestOf<ITrackedEntityAttribute>;

export type NewRestTrackedEntityAttribute = RestOf<NewTrackedEntityAttribute>;

export type PartialUpdateRestTrackedEntityAttribute = RestOf<PartialUpdateTrackedEntityAttribute>;

export type EntityResponseType = HttpResponse<ITrackedEntityAttribute>;
export type EntityArrayResponseType = HttpResponse<ITrackedEntityAttribute[]>;

@Injectable({ providedIn: 'root' })
export class TrackedEntityAttributeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tracked-entity-attributes');

  create(trackedEntityAttribute: NewTrackedEntityAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trackedEntityAttribute);
    return this.http
      .post<RestTrackedEntityAttribute>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(trackedEntityAttribute: ITrackedEntityAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trackedEntityAttribute);
    return this.http
      .put<RestTrackedEntityAttribute>(`${this.resourceUrl}/${this.getTrackedEntityAttributeIdentifier(trackedEntityAttribute)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(trackedEntityAttribute: PartialUpdateTrackedEntityAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trackedEntityAttribute);
    return this.http
      .patch<RestTrackedEntityAttribute>(`${this.resourceUrl}/${this.getTrackedEntityAttributeIdentifier(trackedEntityAttribute)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestTrackedEntityAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTrackedEntityAttribute[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrackedEntityAttributeIdentifier(trackedEntityAttribute: Pick<ITrackedEntityAttribute, 'id'>): string {
    return trackedEntityAttribute.id;
  }

  compareTrackedEntityAttribute(o1: Pick<ITrackedEntityAttribute, 'id'> | null, o2: Pick<ITrackedEntityAttribute, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrackedEntityAttributeIdentifier(o1) === this.getTrackedEntityAttributeIdentifier(o2) : o1 === o2;
  }

  addTrackedEntityAttributeToCollectionIfMissing<Type extends Pick<ITrackedEntityAttribute, 'id'>>(
    trackedEntityAttributeCollection: Type[],
    ...trackedEntityAttributesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trackedEntityAttributes: Type[] = trackedEntityAttributesToCheck.filter(isPresent);
    if (trackedEntityAttributes.length > 0) {
      const trackedEntityAttributeCollectionIdentifiers = trackedEntityAttributeCollection.map(trackedEntityAttributeItem =>
        this.getTrackedEntityAttributeIdentifier(trackedEntityAttributeItem),
      );
      const trackedEntityAttributesToAdd = trackedEntityAttributes.filter(trackedEntityAttributeItem => {
        const trackedEntityAttributeIdentifier = this.getTrackedEntityAttributeIdentifier(trackedEntityAttributeItem);
        if (trackedEntityAttributeCollectionIdentifiers.includes(trackedEntityAttributeIdentifier)) {
          return false;
        }
        trackedEntityAttributeCollectionIdentifiers.push(trackedEntityAttributeIdentifier);
        return true;
      });
      return [...trackedEntityAttributesToAdd, ...trackedEntityAttributeCollection];
    }
    return trackedEntityAttributeCollection;
  }

  protected convertDateFromClient<T extends ITrackedEntityAttribute | NewTrackedEntityAttribute | PartialUpdateTrackedEntityAttribute>(
    trackedEntityAttribute: T,
  ): RestOf<T> {
    return {
      ...trackedEntityAttribute,
      lastUpdated: trackedEntityAttribute.lastUpdated?.toJSON() ?? null,
      created: trackedEntityAttribute.created?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTrackedEntityAttribute: RestTrackedEntityAttribute): ITrackedEntityAttribute {
    return {
      ...restTrackedEntityAttribute,
      lastUpdated: restTrackedEntityAttribute.lastUpdated ? dayjs(restTrackedEntityAttribute.lastUpdated) : undefined,
      created: restTrackedEntityAttribute.created ? dayjs(restTrackedEntityAttribute.created) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTrackedEntityAttribute>): HttpResponse<ITrackedEntityAttribute> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTrackedEntityAttribute[]>): HttpResponse<ITrackedEntityAttribute[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
