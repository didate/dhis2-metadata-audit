import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';

const programRuleVariableResolve = (route: ActivatedRouteSnapshot): Observable<null | IProgramRuleVariable> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProgramRuleVariableService)
      .find(id)
      .pipe(
        mergeMap((programRuleVariable: HttpResponse<IProgramRuleVariable>) => {
          if (programRuleVariable.body) {
            return of(programRuleVariable.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default programRuleVariableResolve;
