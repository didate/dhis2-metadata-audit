import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIndicator } from '../indicator.model';
import { IndicatorService } from '../service/indicator.service';

const indicatorResolve = (route: ActivatedRouteSnapshot): Observable<null | IIndicator> => {
  const id = route.params['id'];
  if (id) {
    return inject(IndicatorService)
      .find(id)
      .pipe(
        mergeMap((indicator: HttpResponse<IIndicator>) => {
          if (indicator.body) {
            return of(indicator.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default indicatorResolve;
