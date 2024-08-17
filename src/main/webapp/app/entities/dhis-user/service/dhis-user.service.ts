import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDHISUser, NewDHISUser } from '../dhis-user.model';

export type PartialUpdateDHISUser = Partial<IDHISUser> & Pick<IDHISUser, 'id'>;

export type EntityResponseType = HttpResponse<IDHISUser>;
export type EntityArrayResponseType = HttpResponse<IDHISUser[]>;

@Injectable({ providedIn: 'root' })
export class DHISUserService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dhis-users');

  create(dHISUser: NewDHISUser): Observable<EntityResponseType> {
    return this.http.post<IDHISUser>(this.resourceUrl, dHISUser, { observe: 'response' });
  }

  update(dHISUser: IDHISUser): Observable<EntityResponseType> {
    return this.http.put<IDHISUser>(`${this.resourceUrl}/${this.getDHISUserIdentifier(dHISUser)}`, dHISUser, { observe: 'response' });
  }

  partialUpdate(dHISUser: PartialUpdateDHISUser): Observable<EntityResponseType> {
    return this.http.patch<IDHISUser>(`${this.resourceUrl}/${this.getDHISUserIdentifier(dHISUser)}`, dHISUser, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDHISUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDHISUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDHISUserIdentifier(dHISUser: Pick<IDHISUser, 'id'>): string {
    return dHISUser.id;
  }

  compareDHISUser(o1: Pick<IDHISUser, 'id'> | null, o2: Pick<IDHISUser, 'id'> | null): boolean {
    return o1 && o2 ? this.getDHISUserIdentifier(o1) === this.getDHISUserIdentifier(o2) : o1 === o2;
  }

  addDHISUserToCollectionIfMissing<Type extends Pick<IDHISUser, 'id'>>(
    dHISUserCollection: Type[],
    ...dHISUsersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dHISUsers: Type[] = dHISUsersToCheck.filter(isPresent);
    if (dHISUsers.length > 0) {
      const dHISUserCollectionIdentifiers = dHISUserCollection.map(dHISUserItem => this.getDHISUserIdentifier(dHISUserItem));
      const dHISUsersToAdd = dHISUsers.filter(dHISUserItem => {
        const dHISUserIdentifier = this.getDHISUserIdentifier(dHISUserItem);
        if (dHISUserCollectionIdentifiers.includes(dHISUserIdentifier)) {
          return false;
        }
        dHISUserCollectionIdentifiers.push(dHISUserIdentifier);
        return true;
      });
      return [...dHISUsersToAdd, ...dHISUserCollection];
    }
    return dHISUserCollection;
  }
}
