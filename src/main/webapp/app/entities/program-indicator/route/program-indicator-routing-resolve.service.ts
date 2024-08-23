import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramIndicator } from '../program-indicator.model';
import { ProgramIndicatorService } from '../service/program-indicator.service';

const programIndicatorResolve = (route: ActivatedRouteSnapshot): Observable<null | IProgramIndicator> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProgramIndicatorService)
      .find(id)
      .pipe(
        mergeMap((programIndicator: HttpResponse<IProgramIndicator>) => {
          if (programIndicator.body) {
            return of(programIndicator.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default programIndicatorResolve;
