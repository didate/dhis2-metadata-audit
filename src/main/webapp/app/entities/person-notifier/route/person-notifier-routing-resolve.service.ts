import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonNotifier } from '../person-notifier.model';
import { PersonNotifierService } from '../service/person-notifier.service';

@Injectable({ providedIn: 'root' })
export class PersonNotifierRoutingResolveService implements Resolve<IPersonNotifier | null> {
  constructor(protected service: PersonNotifierService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonNotifier | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personNotifier: HttpResponse<IPersonNotifier>) => {
          if (personNotifier.body) {
            return of(personNotifier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
