import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIndicator } from '../indicator.model';
import { IndicatorService } from '../service/indicator.service';

@Injectable({ providedIn: 'root' })
export class IndicatorRoutingResolveService implements Resolve<IIndicator | null> {
  constructor(protected service: IndicatorService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicator | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((indicator: HttpResponse<IIndicator>) => {
          if (indicator.body) {
            return of(indicator.body);
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
