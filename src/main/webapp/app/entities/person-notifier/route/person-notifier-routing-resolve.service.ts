import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonNotifier } from '../person-notifier.model';
import { PersonNotifierService } from '../service/person-notifier.service';

const personNotifierResolve = (route: ActivatedRouteSnapshot): Observable<null | IPersonNotifier> => {
  const id = route.params['id'];
  if (id) {
    return inject(PersonNotifierService)
      .find(id)
      .pipe(
        mergeMap((personNotifier: HttpResponse<IPersonNotifier>) => {
          if (personNotifier.body) {
            return of(personNotifier.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default personNotifierResolve;
