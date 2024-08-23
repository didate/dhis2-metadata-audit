import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganisationUnitComponent } from './list/organisation-unit.component';
import { OrganisationUnitDetailComponent } from './detail/organisation-unit-detail.component';
import { OrganisationUnitUpdateComponent } from './update/organisation-unit-update.component';
import OrganisationUnitResolve from './route/organisation-unit-routing-resolve.service';

const organisationUnitRoute: Routes = [
  {
    path: '',
    component: OrganisationUnitComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganisationUnitDetailComponent,
    resolve: {
      organisationUnit: OrganisationUnitResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganisationUnitUpdateComponent,
    resolve: {
      organisationUnit: OrganisationUnitResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganisationUnitUpdateComponent,
    resolve: {
      organisationUnit: OrganisationUnitResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default organisationUnitRoute;
