import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProgramRuleVariable, NewProgramRuleVariable } from '../program-rule-variable.model';

export type PartialUpdateProgramRuleVariable = Partial<IProgramRuleVariable> & Pick<IProgramRuleVariable, 'id'>;

type RestOf<T extends IProgramRuleVariable | NewProgramRuleVariable> = Omit<T, 'lastUpdated' | 'created'> & {
  lastUpdated?: string | null;
  created?: string | null;
};

export type RestProgramRuleVariable = RestOf<IProgramRuleVariable>;

export type NewRestProgramRuleVariable = RestOf<NewProgramRuleVariable>;

export type PartialUpdateRestProgramRuleVariable = RestOf<PartialUpdateProgramRuleVariable>;

export type EntityResponseType = HttpResponse<IProgramRuleVariable>;
export type EntityArrayResponseType = HttpResponse<IProgramRuleVariable[]>;

@Injectable({ providedIn: 'root' })
export class ProgramRuleVariableService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/program-rule-variables');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(programRuleVariable: NewProgramRuleVariable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRuleVariable);
    return this.http
      .post<RestProgramRuleVariable>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(programRuleVariable: IProgramRuleVariable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRuleVariable);
    return this.http
      .put<RestProgramRuleVariable>(`${this.resourceUrl}/${this.getProgramRuleVariableIdentifier(programRuleVariable)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(programRuleVariable: PartialUpdateProgramRuleVariable): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(programRuleVariable);
    return this.http
      .patch<RestProgramRuleVariable>(`${this.resourceUrl}/${this.getProgramRuleVariableIdentifier(programRuleVariable)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestProgramRuleVariable>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProgramRuleVariable[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  history(id: any): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestProgramRuleVariable[]>(`${this.resourceUrl}/${id}/audit`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  compare(id: string, rev1: number, rev2: number): Observable<EntityArrayResponseType> {
    return this.http
      .get<RestProgramRuleVariable[]>(`${this.resourceUrl}/${id}/compare/${rev1}/${rev2}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProgramRuleVariableIdentifier(programRuleVariable: Pick<IProgramRuleVariable, 'id'>): string {
    return programRuleVariable.id;
  }

  compareProgramRuleVariable(o1: Pick<IProgramRuleVariable, 'id'> | null, o2: Pick<IProgramRuleVariable, 'id'> | null): boolean {
    return o1 && o2 ? this.getProgramRuleVariableIdentifier(o1) === this.getProgramRuleVariableIdentifier(o2) : o1 === o2;
  }

  addProgramRuleVariableToCollectionIfMissing<Type extends Pick<IProgramRuleVariable, 'id'>>(
    programRuleVariableCollection: Type[],
    ...programRuleVariablesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const programRuleVariables: Type[] = programRuleVariablesToCheck.filter(isPresent);
    if (programRuleVariables.length > 0) {
      const programRuleVariableCollectionIdentifiers = programRuleVariableCollection.map(
        programRuleVariableItem => this.getProgramRuleVariableIdentifier(programRuleVariableItem)!
      );
      const programRuleVariablesToAdd = programRuleVariables.filter(programRuleVariableItem => {
        const programRuleVariableIdentifier = this.getProgramRuleVariableIdentifier(programRuleVariableItem);
        if (programRuleVariableCollectionIdentifiers.includes(programRuleVariableIdentifier)) {
          return false;
        }
        programRuleVariableCollectionIdentifiers.push(programRuleVariableIdentifier);
        return true;
      });
      return [...programRuleVariablesToAdd, ...programRuleVariableCollection];
    }
    return programRuleVariableCollection;
  }

  protected convertDateFromClient<T extends IProgramRuleVariable | NewProgramRuleVariable | PartialUpdateProgramRuleVariable>(
    programRuleVariable: T
  ): RestOf<T> {
    return {
      ...programRuleVariable,
      lastUpdated: programRuleVariable.lastUpdated?.toJSON() ?? null,
      created: programRuleVariable.created?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProgramRuleVariable: RestProgramRuleVariable): IProgramRuleVariable {
    return {
      ...restProgramRuleVariable,
      lastUpdated: restProgramRuleVariable.lastUpdated ? dayjs.unix(restProgramRuleVariable.lastUpdated as unknown as number) : undefined,
      created: restProgramRuleVariable.created ? dayjs.unix(restProgramRuleVariable.created as unknown as number) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProgramRuleVariable>): HttpResponse<IProgramRuleVariable> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProgramRuleVariable[]>): HttpResponse<IProgramRuleVariable[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
