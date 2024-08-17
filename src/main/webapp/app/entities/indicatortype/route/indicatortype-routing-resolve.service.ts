import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIndicatortype } from '../indicatortype.model';
import { IndicatortypeService } from '../service/indicatortype.service';

const indicatortypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IIndicatortype> => {
  const id = route.params['id'];
  if (id) {
    return inject(IndicatortypeService)
      .find(id)
      .pipe(
        mergeMap((indicatortype: HttpResponse<IIndicatortype>) => {
          if (indicatortype.body) {
            return of(indicatortype.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default indicatortypeResolve;
