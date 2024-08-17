import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataelement } from '../dataelement.model';
import { DataelementService } from '../service/dataelement.service';

const dataelementResolve = (route: ActivatedRouteSnapshot): Observable<null | IDataelement> => {
  const id = route.params['id'];
  if (id) {
    return inject(DataelementService)
      .find(id)
      .pipe(
        mergeMap((dataelement: HttpResponse<IDataelement>) => {
          if (dataelement.body) {
            return of(dataelement.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default dataelementResolve;
