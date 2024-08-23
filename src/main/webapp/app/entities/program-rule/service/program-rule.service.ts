import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramRule, NewProgramRule } from '../program-rule.model';

export type PartialUpdateProgramRule = Partial<IProgramRule> & Pick<IProgramRule, 'id'>;

type RestOf<T extends IProgramRule | NewProgramRule> = Omit<T, 'lastUpdated' | 'created'> & {
  lastUpdated?: string | null;
  created?: string | null;
};

export type RestProgramRule = RestOf<IProgramRule>;

export type NewRestProgramRule = RestOf<NewProgramRule>;

export type PartialUpdateRestProgramRule = RestOf<PartialUpdateProgramRule>;

export type EntityResponseType = HttpResponse<IProgramRule>;
export type EntityArrayResponseType = HttpResponse<IProgramRule[]>;

@Injectable({ providedIn: 'root' })
export class ProgramRuleService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/program-rules');

  create(programRule: NewProgramRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRule);
    return this.http
      .post<RestProgramRule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(programRule: IProgramRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRule);
    return this.http
      .put<RestProgramRule>(`${this.resourceUrl}/${this.getProgramRuleIdentifier(programRule)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(programRule: PartialUpdateProgramRule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRule);
    return this.http
      .patch<RestProgramRule>(`${this.resourceUrl}/${this.getProgramRuleIdentifier(programRule)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProgramRule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProgramRule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProgramRuleIdentifier(programRule: Pick<IProgramRule, 'id'>): string {
    return programRule.id;
  }

  compareProgramRule(o1: Pick<IProgramRule, 'id'> | null, o2: Pick<IProgramRule, 'id'> | null): boolean {
    return o1 && o2 ? this.getProgramRuleIdentifier(o1) === this.getProgramRuleIdentifier(o2) : o1 === o2;
  }

  addProgramRuleToCollectionIfMissing<Type extends Pick<IProgramRule, 'id'>>(
    programRuleCollection: Type[],
    ...programRulesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const programRules: Type[] = programRulesToCheck.filter(isPresent);
    if (programRules.length > 0) {
      const programRuleCollectionIdentifiers = programRuleCollection.map(programRuleItem => this.getProgramRuleIdentifier(programRuleItem));
      const programRulesToAdd = programRules.filter(programRuleItem => {
        const programRuleIdentifier = this.getProgramRuleIdentifier(programRuleItem);
        if (programRuleCollectionIdentifiers.includes(programRuleIdentifier)) {
          return false;
        }
        programRuleCollectionIdentifiers.push(programRuleIdentifier);
        return true;
      });
      return [...programRulesToAdd, ...programRuleCollection];
    }
    return programRuleCollection;
  }

  protected convertDateFromClient<T extends IProgramRule | NewProgramRule | PartialUpdateProgramRule>(programRule: T): RestOf<T> {
    return {
      ...programRule,
      lastUpdated: programRule.lastUpdated?.toJSON() ?? null,
      created: programRule.created?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProgramRule: RestProgramRule): IProgramRule {
    return {
      ...restProgramRule,
      lastUpdated: restProgramRule.lastUpdated ? dayjs(restProgramRule.lastUpdated) : undefined,
      created: restProgramRule.created ? dayjs(restProgramRule.created) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProgramRule>): HttpResponse<IProgramRule> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProgramRule[]>): HttpResponse<IProgramRule[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
