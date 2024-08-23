import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';

const programRuleResolve = (route: ActivatedRouteSnapshot): Observable<null | IProgramRule> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProgramRuleService)
      .find(id)
      .pipe(
        mergeMap((programRule: HttpResponse<IProgramRule>) => {
          if (programRule.body) {
            return of(programRule.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default programRuleResolve;
