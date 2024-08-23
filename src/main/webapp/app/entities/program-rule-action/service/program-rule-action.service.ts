import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramRuleAction, NewProgramRuleAction } from '../program-rule-action.model';

export type PartialUpdateProgramRuleAction = Partial<IProgramRuleAction> & Pick<IProgramRuleAction, 'id'>;

type RestOf<T extends IProgramRuleAction | NewProgramRuleAction> = Omit<T, 'lastUpdated'> & {
  lastUpdated?: string | null;
};

export type RestProgramRuleAction = RestOf<IProgramRuleAction>;

export type NewRestProgramRuleAction = RestOf<NewProgramRuleAction>;

export type PartialUpdateRestProgramRuleAction = RestOf<PartialUpdateProgramRuleAction>;

export type EntityResponseType = HttpResponse<IProgramRuleAction>;
export type EntityArrayResponseType = HttpResponse<IProgramRuleAction[]>;

@Injectable({ providedIn: 'root' })
export class ProgramRuleActionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/program-rule-actions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(programRuleAction: NewProgramRuleAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRuleAction);
    return this.http
      .post<RestProgramRuleAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(programRuleAction: IProgramRuleAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRuleAction);
    return this.http
      .put<RestProgramRuleAction>(`${this.resourceUrl}/${this.getProgramRuleActionIdentifier(programRuleAction)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(programRuleAction: PartialUpdateProgramRuleAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRuleAction);
    return this.http
      .patch<RestProgramRuleAction>(`${this.resourceUrl}/${this.getProgramRuleActionIdentifier(programRuleAction)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProgramRuleAction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProgramRuleAction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProgramRuleActionIdentifier(programRuleAction: Pick<IProgramRuleAction, 'id'>): string {
    return programRuleAction.id;
  }

  compareProgramRuleAction(o1: Pick<IProgramRuleAction, 'id'> | null, o2: Pick<IProgramRuleAction, 'id'> | null): boolean {
    return o1 && o2 ? this.getProgramRuleActionIdentifier(o1) === this.getProgramRuleActionIdentifier(o2) : o1 === o2;
  }

  addProgramRuleActionToCollectionIfMissing<Type extends Pick<IProgramRuleAction, 'id'>>(
    programRuleActionCollection: Type[],
    ...programRuleActionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const programRuleActions: Type[] = programRuleActionsToCheck.filter(isPresent);
    if (programRuleActions.length > 0) {
      const programRuleActionCollectionIdentifiers = programRuleActionCollection.map(
        programRuleActionItem => this.getProgramRuleActionIdentifier(programRuleActionItem)!
      );
      const programRuleActionsToAdd = programRuleActions.filter(programRuleActionItem => {
        const programRuleActionIdentifier = this.getProgramRuleActionIdentifier(programRuleActionItem);
        if (programRuleActionCollectionIdentifiers.includes(programRuleActionIdentifier)) {
          return false;
        }
        programRuleActionCollectionIdentifiers.push(programRuleActionIdentifier);
        return true;
      });
      return [...programRuleActionsToAdd, ...programRuleActionCollection];
    }
    return programRuleActionCollection;
  }

  protected convertDateFromClient<T extends IProgramRuleAction | NewProgramRuleAction | PartialUpdateProgramRuleAction>(
    programRuleAction: T
  ): RestOf<T> {
    return {
      ...programRuleAction,
      lastUpdated: programRuleAction.lastUpdated?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProgramRuleAction: RestProgramRuleAction): IProgramRuleAction {
    return {
      ...restProgramRuleAction,
      lastUpdated: restProgramRuleAction.lastUpdated ? dayjs(restProgramRuleAction.lastUpdated) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProgramRuleAction>): HttpResponse<IProgramRuleAction> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProgramRuleAction[]>): HttpResponse<IProgramRuleAction[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
