import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramStage } from '../program-stage.model';
import { ProgramStageService } from '../service/program-stage.service';

@Injectable({ providedIn: 'root' })
export class ProgramStageRoutingResolveService implements Resolve<IProgramStage | null> {
  constructor(protected service: ProgramStageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramStage | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programStage: HttpResponse<IProgramStage>) => {
          if (programStage.body) {
            return of(programStage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IProgramStage = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
