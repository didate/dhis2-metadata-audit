import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataset } from '../dataset.model';
import { DatasetService } from '../service/dataset.service';

@Injectable({ providedIn: 'root' })
export class DatasetRoutingResolveService implements Resolve<IDataset | null> {
  constructor(protected service: DatasetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDataset | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dataset: HttpResponse<IDataset>) => {
          if (dataset.body) {
            return of(dataset.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: IDataset = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
