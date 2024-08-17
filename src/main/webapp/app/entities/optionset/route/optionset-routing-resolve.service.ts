import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOptionset } from '../optionset.model';
import { OptionsetService } from '../service/optionset.service';

const optionsetResolve = (route: ActivatedRouteSnapshot): Observable<null | IOptionset> => {
  const id = route.params['id'];
  if (id) {
    return inject(OptionsetService)
      .find(id)
      .pipe(
        mergeMap((optionset: HttpResponse<IOptionset>) => {
          if (optionset.body) {
            return of(optionset.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default optionsetResolve;
