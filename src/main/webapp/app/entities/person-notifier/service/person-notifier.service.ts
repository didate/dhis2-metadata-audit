import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPersonNotifier, NewPersonNotifier } from '../person-notifier.model';

export type PartialUpdatePersonNotifier = Partial<IPersonNotifier> & Pick<IPersonNotifier, 'id'>;

export type EntityResponseType = HttpResponse<IPersonNotifier>;
export type EntityArrayResponseType = HttpResponse<IPersonNotifier[]>;

@Injectable({ providedIn: 'root' })
export class PersonNotifierService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/person-notifiers');

  create(personNotifier: NewPersonNotifier): Observable<EntityResponseType> {
    return this.http.post<IPersonNotifier>(this.resourceUrl, personNotifier, { observe: 'response' });
  }

  update(personNotifier: IPersonNotifier): Observable<EntityResponseType> {
    return this.http.put<IPersonNotifier>(`${this.resourceUrl}/${this.getPersonNotifierIdentifier(personNotifier)}`, personNotifier, {
      observe: 'response',
    });
  }

  partialUpdate(personNotifier: PartialUpdatePersonNotifier): Observable<EntityResponseType> {
    return this.http.patch<IPersonNotifier>(`${this.resourceUrl}/${this.getPersonNotifierIdentifier(personNotifier)}`, personNotifier, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPersonNotifier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPersonNotifier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPersonNotifierIdentifier(personNotifier: Pick<IPersonNotifier, 'id'>): number {
    return personNotifier.id;
  }

  comparePersonNotifier(o1: Pick<IPersonNotifier, 'id'> | null, o2: Pick<IPersonNotifier, 'id'> | null): boolean {
    return o1 && o2 ? this.getPersonNotifierIdentifier(o1) === this.getPersonNotifierIdentifier(o2) : o1 === o2;
  }

  addPersonNotifierToCollectionIfMissing<Type extends Pick<IPersonNotifier, 'id'>>(
    personNotifierCollection: Type[],
    ...personNotifiersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const personNotifiers: Type[] = personNotifiersToCheck.filter(isPresent);
    if (personNotifiers.length > 0) {
      const personNotifierCollectionIdentifiers = personNotifierCollection.map(personNotifierItem =>
        this.getPersonNotifierIdentifier(personNotifierItem),
      );
      const personNotifiersToAdd = personNotifiers.filter(personNotifierItem => {
        const personNotifierIdentifier = this.getPersonNotifierIdentifier(personNotifierItem);
        if (personNotifierCollectionIdentifiers.includes(personNotifierIdentifier)) {
          return false;
        }
        personNotifierCollectionIdentifiers.push(personNotifierIdentifier);
        return true;
      });
      return [...personNotifiersToAdd, ...personNotifierCollection];
    }
    return personNotifierCollection;
  }
}
