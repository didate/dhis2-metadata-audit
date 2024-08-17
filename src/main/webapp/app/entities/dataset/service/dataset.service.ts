import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataset, NewDataset } from '../dataset.model';

export type PartialUpdateDataset = Partial<IDataset> & Pick<IDataset, 'id'>;

type RestOf<T extends IDataset | NewDataset> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestDataset = RestOf<IDataset>;

export type NewRestDataset = RestOf<NewDataset>;

export type PartialUpdateRestDataset = RestOf<PartialUpdateDataset>;

export type EntityResponseType = HttpResponse<IDataset>;
export type EntityArrayResponseType = HttpResponse<IDataset[]>;

@Injectable({ providedIn: 'root' })
export class DatasetService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/datasets');

  create(dataset: NewDataset): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataset);
    return this.http
      .post<RestDataset>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dataset: IDataset): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataset);
    return this.http
      .put<RestDataset>(`${this.resourceUrl}/${this.getDatasetIdentifier(dataset)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dataset: PartialUpdateDataset): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dataset);
    return this.http
      .patch<RestDataset>(`${this.resourceUrl}/${this.getDatasetIdentifier(dataset)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestDataset>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDataset[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDatasetIdentifier(dataset: Pick<IDataset, 'id'>): string {
    return dataset.id;
  }

  compareDataset(o1: Pick<IDataset, 'id'> | null, o2: Pick<IDataset, 'id'> | null): boolean {
    return o1 && o2 ? this.getDatasetIdentifier(o1) === this.getDatasetIdentifier(o2) : o1 === o2;
  }

  addDatasetToCollectionIfMissing<Type extends Pick<IDataset, 'id'>>(
    datasetCollection: Type[],
    ...datasetsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const datasets: Type[] = datasetsToCheck.filter(isPresent);
    if (datasets.length > 0) {
      const datasetCollectionIdentifiers = datasetCollection.map(datasetItem => this.getDatasetIdentifier(datasetItem));
      const datasetsToAdd = datasets.filter(datasetItem => {
        const datasetIdentifier = this.getDatasetIdentifier(datasetItem);
        if (datasetCollectionIdentifiers.includes(datasetIdentifier)) {
          return false;
        }
        datasetCollectionIdentifiers.push(datasetIdentifier);
        return true;
      });
      return [...datasetsToAdd, ...datasetCollection];
    }
    return datasetCollection;
  }

  protected convertDateFromClient<T extends IDataset | NewDataset | PartialUpdateDataset>(dataset: T): RestOf<T> {
    return {
      ...dataset,
      created: dataset.created?.toJSON() ?? null,
      lastUpdated: dataset.lastUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDataset: RestDataset): IDataset {
    return {
      ...restDataset,
      created: restDataset.created ? dayjs(restDataset.created) : undefined,
      lastUpdated: restDataset.lastUpdated ? dayjs(restDataset.lastUpdated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDataset>): HttpResponse<IDataset> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDataset[]>): HttpResponse<IDataset[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
