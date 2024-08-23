import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIndicatortype } from '../indicatortype.model';
import { IndicatortypeService } from '../service/indicatortype.service';

@Injectable({ providedIn: 'root' })
export class IndicatortypeRoutingResolveService implements Resolve<IIndicatortype | null> {
  constructor(protected service: IndicatortypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicatortype | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((indicatortype: HttpResponse<IIndicatortype>) => {
          if (indicatortype.body) {
            return of(indicatortype.body);
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
