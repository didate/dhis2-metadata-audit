import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';

@Injectable({ providedIn: 'root' })
export class ProgramRuleActionRoutingResolveService implements Resolve<IProgramRuleAction | null> {
  constructor(protected service: ProgramRuleActionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramRuleAction | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programRuleAction: HttpResponse<IProgramRuleAction>) => {
          if (programRuleAction.body) {
            return of(programRuleAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IProgramRuleAction = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
