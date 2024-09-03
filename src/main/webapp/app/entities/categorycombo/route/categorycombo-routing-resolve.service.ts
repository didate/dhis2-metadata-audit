import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorycombo } from '../categorycombo.model';
import { CategorycomboService } from '../service/categorycombo.service';

@Injectable({ providedIn: 'root' })
export class CategorycomboRoutingResolveService implements Resolve<ICategorycombo | null> {
  constructor(protected service: CategorycomboService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorycombo | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorycombo: HttpResponse<ICategorycombo>) => {
          if (categorycombo.body) {
            return of(categorycombo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: ICategorycombo = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
