import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrganisationUnit, NewOrganisationUnit } from '../organisation-unit.model';

export type PartialUpdateOrganisationUnit = Partial<IOrganisationUnit> & Pick<IOrganisationUnit, 'id'>;

type RestOf<T extends IOrganisationUnit | NewOrganisationUnit> = Omit<T, 'created' | 'lastUpdated' | 'openingDate'> & {
  created?: string | null;
  lastUpdated?: string | null;
  openingDate?: string | null;
};

export type RestOrganisationUnit = RestOf<IOrganisationUnit>;

export type NewRestOrganisationUnit = RestOf<NewOrganisationUnit>;

export type PartialUpdateRestOrganisationUnit = RestOf<PartialUpdateOrganisationUnit>;

export type EntityResponseType = HttpResponse<IOrganisationUnit>;
export type EntityArrayResponseType = HttpResponse<IOrganisationUnit[]>;

@Injectable({ providedIn: 'root' })
export class OrganisationUnitService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/organisation-units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(organisationUnit: NewOrganisationUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisationUnit);
    return this.http
      .post<RestOrganisationUnit>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(organisationUnit: IOrganisationUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisationUnit);
    return this.http
      .put<RestOrganisationUnit>(`${this.resourceUrl}/${this.getOrganisationUnitIdentifier(organisationUnit)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(organisationUnit: PartialUpdateOrganisationUnit): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisationUnit);
    return this.http
      .patch<RestOrganisationUnit>(`${this.resourceUrl}/${this.getOrganisationUnitIdentifier(organisationUnit)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestOrganisationUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrganisationUnit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestOrganisationUnit[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestOrganisationUnit[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrganisationUnitIdentifier(organisationUnit: Pick<IOrganisationUnit, 'id'>): string {
    return organisationUnit.id;
  }

  compareOrganisationUnit(o1: Pick<IOrganisationUnit, 'id'> | null, o2: Pick<IOrganisationUnit, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrganisationUnitIdentifier(o1) === this.getOrganisationUnitIdentifier(o2) : o1 === o2;
  }

  addOrganisationUnitToCollectionIfMissing<Type extends Pick<IOrganisationUnit, 'id'>>(
    organisationUnitCollection: Type[],
    ...organisationUnitsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const organisationUnits: Type[] = organisationUnitsToCheck.filter(isPresent);
    if (organisationUnits.length > 0) {
      const organisationUnitCollectionIdentifiers = organisationUnitCollection.map(
        organisationUnitItem => this.getOrganisationUnitIdentifier(organisationUnitItem)!
      );
      const organisationUnitsToAdd = organisationUnits.filter(organisationUnitItem => {
        const organisationUnitIdentifier = this.getOrganisationUnitIdentifier(organisationUnitItem);
        if (organisationUnitCollectionIdentifiers.includes(organisationUnitIdentifier)) {
          return false;
        }
        organisationUnitCollectionIdentifiers.push(organisationUnitIdentifier);
        return true;
      });
      return [...organisationUnitsToAdd, ...organisationUnitCollection];
    }
    return organisationUnitCollection;
  }

  protected convertDateFromClient<T extends IOrganisationUnit | NewOrganisationUnit | PartialUpdateOrganisationUnit>(
    organisationUnit: T
  ): RestOf<T> {
    return {
      ...organisationUnit,
      created: organisationUnit.created?.toJSON() ?? null,
      lastUpdated: organisationUnit.lastUpdated?.toJSON() ?? null,
      openingDate: organisationUnit.openingDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restOrganisationUnit: RestOrganisationUnit): IOrganisationUnit {
    return {
      ...restOrganisationUnit,
      created: restOrganisationUnit.created ? dayjs.unix(restOrganisationUnit.created as unknown as number) : undefined,
      lastUpdated: restOrganisationUnit.lastUpdated ? dayjs.unix(restOrganisationUnit.lastUpdated as unknown as number) : undefined,
      openingDate: restOrganisationUnit.openingDate ? dayjs.unix(restOrganisationUnit.openingDate as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrganisationUnit>): HttpResponse<IOrganisationUnit> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrganisationUnit[]>): HttpResponse<IOrganisationUnit[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
