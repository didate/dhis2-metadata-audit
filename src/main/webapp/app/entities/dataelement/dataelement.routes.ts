import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataelementComponent } from './list/dataelement.component';
import { DataelementDetailComponent } from './detail/dataelement-detail.component';
import { DataelementUpdateComponent } from './update/dataelement-update.component';
import DataelementResolve from './route/dataelement-routing-resolve.service';

const dataelementRoute: Routes = [
  {
    path: '',
    component: DataelementComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataelementDetailComponent,
    resolve: {
      dataelement: DataelementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataelementUpdateComponent,
    resolve: {
      dataelement: DataelementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataelementUpdateComponent,
    resolve: {
      dataelement: DataelementResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dataelementRoute;
