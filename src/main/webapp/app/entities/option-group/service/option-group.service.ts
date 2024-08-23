import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOptionGroup, NewOptionGroup } from '../option-group.model';

export type EntityResponseType = HttpResponse<IOptionGroup>;
export type EntityArrayResponseType = HttpResponse<IOptionGroup[]>;

@Injectable({ providedIn: 'root' })
export class OptionGroupService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/option-groups');

  create(optionGroup: NewOptionGroup): Observable<EntityResponseType> {
    return this.http.post<IOptionGroup>(this.resourceUrl, optionGroup, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IOptionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOptionGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
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
      const optionGroupCollectionIdentifiers = optionGroupCollection.map(optionGroupItem => this.getOptionGroupIdentifier(optionGroupItem));
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
}
