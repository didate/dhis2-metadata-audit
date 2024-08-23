import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOptionGroup } from '../option-group.model';
import { OptionGroupService } from '../service/option-group.service';

const optionGroupResolve = (route: ActivatedRouteSnapshot): Observable<null | IOptionGroup> => {
  const id = route.params['id'];
  if (id) {
    return inject(OptionGroupService)
      .find(id)
      .pipe(
        mergeMap((optionGroup: HttpResponse<IOptionGroup>) => {
          if (optionGroup.body) {
            return of(optionGroup.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default optionGroupResolve;
