import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorycombo } from '../categorycombo.model';
import { CategorycomboService } from '../service/categorycombo.service';

const categorycomboResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategorycombo> => {
  const id = route.params['id'];
  if (id) {
    return inject(CategorycomboService)
      .find(id)
      .pipe(
        mergeMap((categorycombo: HttpResponse<ICategorycombo>) => {
          if (categorycombo.body) {
            return of(categorycombo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default categorycomboResolve;
