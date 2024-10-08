import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorycombo, NewCategorycombo } from '../categorycombo.model';
import dayjs from 'dayjs/esm';

type RestOf<T extends ICategorycombo | NewCategorycombo> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

export type RestCategorycombo = RestOf<ICategorycombo>;

export type PartialUpdateCategorycombo = Partial<ICategorycombo> & Pick<ICategorycombo, 'id'>;

export type EntityResponseType = HttpResponse<ICategorycombo>;
export type EntityArrayResponseType = HttpResponse<ICategorycombo[]>;

@Injectable({ providedIn: 'root' })
export class CategorycomboService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorycombos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categorycombo: NewCategorycombo): Observable<EntityResponseType> {
    return this.http.post<ICategorycombo>(this.resourceUrl, categorycombo, { observe: 'response' });
  }

  update(categorycombo: ICategorycombo): Observable<EntityResponseType> {
    return this.http.put<ICategorycombo>(`${this.resourceUrl}/${this.getCategorycomboIdentifier(categorycombo)}`, categorycombo, {
      observe: 'response',
    });
  }

  partialUpdate(categorycombo: PartialUpdateCategorycombo): Observable<EntityResponseType> {
    return this.http.patch<ICategorycombo>(`${this.resourceUrl}/${this.getCategorycomboIdentifier(categorycombo)}`, categorycombo, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ICategorycombo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCategorycombo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestCategorycombo[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestCategorycombo[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategorycomboIdentifier(categorycombo: Pick<ICategorycombo, 'id'>): string {
    return categorycombo.id;
  }

  compareCategorycombo(o1: Pick<ICategorycombo, 'id'> | null, o2: Pick<ICategorycombo, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategorycomboIdentifier(o1) === this.getCategorycomboIdentifier(o2) : o1 === o2;
  }

  addCategorycomboToCollectionIfMissing<Type extends Pick<ICategorycombo, 'id'>>(
    categorycomboCollection: Type[],
    ...categorycombosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorycombos: Type[] = categorycombosToCheck.filter(isPresent);
    if (categorycombos.length > 0) {
      const categorycomboCollectionIdentifiers = categorycomboCollection.map(
        categorycomboItem => this.getCategorycomboIdentifier(categorycomboItem)!
      );
      const categorycombosToAdd = categorycombos.filter(categorycomboItem => {
        const categorycomboIdentifier = this.getCategorycomboIdentifier(categorycomboItem);
        if (categorycomboCollectionIdentifiers.includes(categorycomboIdentifier)) {
          return false;
        }
        categorycomboCollectionIdentifiers.push(categorycomboIdentifier);
        return true;
      });
      return [...categorycombosToAdd, ...categorycomboCollection];
    }
    return categorycomboCollection;
  }

  protected convertDateFromServer(restOrganisationUnit: RestCategorycombo): ICategorycombo {
    return {
      ...restOrganisationUnit,
      created: restOrganisationUnit.created ? dayjs.unix(restOrganisationUnit.created as unknown as number) : undefined,
      lastUpdated: restOrganisationUnit.lastUpdated ? dayjs.unix(restOrganisationUnit.lastUpdated as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCategorycombo>): HttpResponse<ICategorycombo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCategorycombo[]>): HttpResponse<ICategorycombo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
