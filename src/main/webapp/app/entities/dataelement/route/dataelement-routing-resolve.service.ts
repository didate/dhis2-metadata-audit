import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataelement } from '../dataelement.model';
import { DataelementService } from '../service/dataelement.service';

@Injectable({ providedIn: 'root' })
export class DataelementRoutingResolveService implements Resolve<IDataelement | null> {
  constructor(protected service: DataelementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDataelement | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dataelement: HttpResponse<IDataelement>) => {
          if (dataelement.body) {
            return of(dataelement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IDataelement = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
