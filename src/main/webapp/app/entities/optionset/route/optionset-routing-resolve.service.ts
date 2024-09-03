import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOptionset } from '../optionset.model';
import { OptionsetService } from '../service/optionset.service';

@Injectable({ providedIn: 'root' })
export class OptionsetRoutingResolveService implements Resolve<IOptionset | null> {
  constructor(protected service: OptionsetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOptionset | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((optionset: HttpResponse<IOptionset>) => {
          if (optionset.body) {
            return of(optionset.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IOptionset = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
