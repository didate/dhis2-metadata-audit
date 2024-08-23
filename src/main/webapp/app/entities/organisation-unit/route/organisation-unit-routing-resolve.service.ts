import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganisationUnit } from '../organisation-unit.model';
import { OrganisationUnitService } from '../service/organisation-unit.service';

const organisationUnitResolve = (route: ActivatedRouteSnapshot): Observable<null | IOrganisationUnit> => {
  const id = route.params['id'];
  if (id) {
    return inject(OrganisationUnitService)
      .find(id)
      .pipe(
        mergeMap((organisationUnit: HttpResponse<IOrganisationUnit>) => {
          if (organisationUnit.body) {
            return of(organisationUnit.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default organisationUnitResolve;
