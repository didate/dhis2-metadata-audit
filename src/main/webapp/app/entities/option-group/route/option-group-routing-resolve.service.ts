import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOptionGroup } from '../option-group.model';
import { OptionGroupService } from '../service/option-group.service';

@Injectable({ providedIn: 'root' })
export class OptionGroupRoutingResolveService implements Resolve<IOptionGroup | null> {
  constructor(protected service: OptionGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOptionGroup | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((optionGroup: HttpResponse<IOptionGroup>) => {
          if (optionGroup.body) {
            return of(optionGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IOptionGroup = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
