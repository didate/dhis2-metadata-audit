import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDHISUser, NewDHISUser } from '../dhis-user.model';

export type PartialUpdateDHISUser = Partial<IDHISUser> & Pick<IDHISUser, 'id'>;

type RestOf<T extends IDHISUser | NewDHISUser> = Omit<T, 'lastLogin' | 'passwordLastUpdated' | 'created' | 'lastUpdated'> & {
  lastLogin?: string | null;
  passwordLastUpdated?: string | null;
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestDHISUser = RestOf<IDHISUser>;

export type NewRestDHISUser = RestOf<NewDHISUser>;

export type PartialUpdateRestDHISUser = RestOf<PartialUpdateDHISUser>;

export type EntityResponseType = HttpResponse<IDHISUser>;
export type EntityArrayResponseType = HttpResponse<IDHISUser[]>;

@Injectable({ providedIn: 'root' })
export class DHISUserService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dhis-users');

  create(dHISUser: NewDHISUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dHISUser);
    return this.http
      .post<RestDHISUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dHISUser: IDHISUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dHISUser);
    return this.http
      .put<RestDHISUser>(`${this.resourceUrl}/${this.getDHISUserIdentifier(dHISUser)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dHISUser: PartialUpdateDHISUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dHISUser);
    return this.http
      .patch<RestDHISUser>(`${this.resourceUrl}/${this.getDHISUserIdentifier(dHISUser)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestDHISUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDHISUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IDHISUser | NewDHISUser | PartialUpdateDHISUser>(dHISUser: T): RestOf<T> {
    return {
      ...dHISUser,
      lastLogin: dHISUser.lastLogin?.toJSON() ?? null,
      passwordLastUpdated: dHISUser.passwordLastUpdated?.toJSON() ?? null,
      created: dHISUser.created?.toJSON() ?? null,
      lastUpdated: dHISUser.lastUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDHISUser: RestDHISUser): IDHISUser {
    return {
      ...restDHISUser,
      lastLogin: restDHISUser.lastLogin ? dayjs(restDHISUser.lastLogin) : undefined,
      passwordLastUpdated: restDHISUser.passwordLastUpdated ? dayjs(restDHISUser.passwordLastUpdated) : undefined,
      created: restDHISUser.created ? dayjs(restDHISUser.created) : undefined,
      lastUpdated: restDHISUser.lastUpdated ? dayjs(restDHISUser.lastUpdated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDHISUser>): HttpResponse<IDHISUser> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDHISUser[]>): HttpResponse<IDHISUser[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
