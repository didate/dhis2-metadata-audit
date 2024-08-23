import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';

const programRuleActionResolve = (route: ActivatedRouteSnapshot): Observable<null | IProgramRuleAction> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProgramRuleActionService)
      .find(id)
      .pipe(
        mergeMap((programRuleAction: HttpResponse<IProgramRuleAction>) => {
          if (programRuleAction.body) {
            return of(programRuleAction.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default programRuleActionResolve;
