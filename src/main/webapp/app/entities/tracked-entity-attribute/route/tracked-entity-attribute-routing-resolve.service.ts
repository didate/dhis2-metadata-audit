import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';

@Injectable({ providedIn: 'root' })
export class TrackedEntityAttributeRoutingResolveService implements Resolve<ITrackedEntityAttribute | null> {
  constructor(protected service: TrackedEntityAttributeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrackedEntityAttribute | null | never> {
    const id = route.params['id'];
    const historyId = route.params['historyId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trackedEntityAttribute: HttpResponse<ITrackedEntityAttribute>) => {
          if (trackedEntityAttribute.body) {
            return of(trackedEntityAttribute.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    } else if (historyId) {
      let current: ITrackedEntityAttribute = { id: historyId };
      return of(current);
    }
    return of(null);
  }
}
