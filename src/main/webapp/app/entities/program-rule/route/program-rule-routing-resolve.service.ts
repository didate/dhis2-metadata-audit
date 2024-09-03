import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';

@Injectable({ providedIn: 'root' })
export class ProgramRuleRoutingResolveService implements Resolve<IProgramRule | null> {
  constructor(protected service: ProgramRuleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramRule | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programRule: HttpResponse<IProgramRule>) => {
          if (programRule.body) {
            return of(programRule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IProgramRule = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
