import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramIndicator } from '../program-indicator.model';
import { ProgramIndicatorService } from '../service/program-indicator.service';

@Injectable({ providedIn: 'root' })
export class ProgramIndicatorRoutingResolveService implements Resolve<IProgramIndicator | null> {
  constructor(protected service: ProgramIndicatorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramIndicator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((programIndicator: HttpResponse<IProgramIndicator>) => {
          if (programIndicator.body) {
            return of(programIndicator.body);
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
