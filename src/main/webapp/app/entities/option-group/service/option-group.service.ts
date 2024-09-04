import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOptionGroup, NewOptionGroup } from '../option-group.model';
import dayjs from 'dayjs/esm';

type RestOf<T extends IOptionGroup | NewOptionGroup> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestOptiongroup = RestOf<IOptionGroup>;

export type PartialUpdateOptionGroup = Partial<IOptionGroup> & Pick<IOptionGroup, 'id'>;

export type EntityResponseType = HttpResponse<IOptionGroup>;
export type EntityArrayResponseType = HttpResponse<IOptionGroup[]>;

@Injectable({ providedIn: 'root' })
export class OptionGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/option-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(optionGroup: NewOptionGroup): Observable<EntityResponseType> {
    return this.http.post<IOptionGroup>(this.resourceUrl, optionGroup, { observe: 'response' });
  }

  update(optionGroup: IOptionGroup): Observable<EntityResponseType> {
    return this.http.put<IOptionGroup>(`${this.resourceUrl}/${this.getOptionGroupIdentifier(optionGroup)}`, optionGroup, {
      observe: 'response',
    });
  }

  partialUpdate(optionGroup: PartialUpdateOptionGroup): Observable<EntityResponseType> {
    return this.http.patch<IOptionGroup>(`${this.resourceUrl}/${this.getOptionGroupIdentifier(optionGroup)}`, optionGroup, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IOptionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOptiongroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestOptiongroup[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestOptiongroup[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOptionGroupIdentifier(optionGroup: Pick<IOptionGroup, 'id'>): string {
    return optionGroup.id;
  }

  compareOptionGroup(o1: Pick<IOptionGroup, 'id'> | null, o2: Pick<IOptionGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getOptionGroupIdentifier(o1) === this.getOptionGroupIdentifier(o2) : o1 === o2;
  }

  addOptionGroupToCollectionIfMissing<Type extends Pick<IOptionGroup, 'id'>>(
    optionGroupCollection: Type[],
    ...optionGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const optionGroups: Type[] = optionGroupsToCheck.filter(isPresent);
    if (optionGroups.length > 0) {
      const optionGroupCollectionIdentifiers = optionGroupCollection.map(
        optionGroupItem => this.getOptionGroupIdentifier(optionGroupItem)!
      );
      const optionGroupsToAdd = optionGroups.filter(optionGroupItem => {
        const optionGroupIdentifier = this.getOptionGroupIdentifier(optionGroupItem);
        if (optionGroupCollectionIdentifiers.includes(optionGroupIdentifier)) {
          return false;
        }
        optionGroupCollectionIdentifiers.push(optionGroupIdentifier);
        return true;
      });
      return [...optionGroupsToAdd, ...optionGroupCollection];
    }
    return optionGroupCollection;
  }

  protected convertDateFromServer(restOptionset: RestOptiongroup): IOptionGroup {
    return {
      ...restOptionset,
      created: restOptionset.created ? dayjs.unix(restOptionset.created as unknown as number) : undefined,
      lastUpdated: restOptionset.lastUpdated ? dayjs.unix(restOptionset.lastUpdated as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOptiongroup>): HttpResponse<IOptionGroup> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOptiongroup[]>): HttpResponse<IOptionGroup[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
