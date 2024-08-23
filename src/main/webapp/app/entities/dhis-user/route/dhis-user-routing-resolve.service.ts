import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDHISUser } from '../dhis-user.model';
import { DHISUserService } from '../service/dhis-user.service';

@Injectable({ providedIn: 'root' })
export class DHISUserRoutingResolveService implements Resolve<IDHISUser | null> {
  constructor(protected service: DHISUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDHISUser | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dHISUser: HttpResponse<IDHISUser>) => {
          if (dHISUser.body) {
            return of(dHISUser.body);
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
