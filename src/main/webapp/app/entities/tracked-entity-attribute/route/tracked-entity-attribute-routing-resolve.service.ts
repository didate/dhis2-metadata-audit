import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';

const trackedEntityAttributeResolve = (route: ActivatedRouteSnapshot): Observable<null | ITrackedEntityAttribute> => {
  const id = route.params['id'];
  if (id) {
    return inject(TrackedEntityAttributeService)
      .find(id)
      .pipe(
        mergeMap((trackedEntityAttribute: HttpResponse<ITrackedEntityAttribute>) => {
          if (trackedEntityAttribute.body) {
            return of(trackedEntityAttribute.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default trackedEntityAttributeResolve;
