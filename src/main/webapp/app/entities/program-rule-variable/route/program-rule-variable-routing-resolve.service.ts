import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';

@Injectable({ providedIn: 'root' })
export class ProgramRuleVariableRoutingResolveService implements Resolve<IProgramRuleVariable | null> {
  constructor(protected service: ProgramRuleVariableService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramRuleVariable | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programRuleVariable: HttpResponse<IProgramRuleVariable>) => {
          if (programRuleVariable.body) {
            return of(programRuleVariable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
