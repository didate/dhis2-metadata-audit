import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDHISUser } from '../dhis-user.model';
import { DHISUserService } from '../service/dhis-user.service';

const dHISUserResolve = (route: ActivatedRouteSnapshot): Observable<null | IDHISUser> => {
  const id = route.params['id'];
  if (id) {
    return inject(DHISUserService)
      .find(id)
      .pipe(
        mergeMap((dHISUser: HttpResponse<IDHISUser>) => {
          if (dHISUser.body) {
            return of(dHISUser.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default dHISUserResolve;
