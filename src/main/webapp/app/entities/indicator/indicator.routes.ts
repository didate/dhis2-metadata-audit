import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IndicatorComponent } from './list/indicator.component';
import { IndicatorDetailComponent } from './detail/indicator-detail.component';
import { IndicatorUpdateComponent } from './update/indicator-update.component';
import IndicatorResolve from './route/indicator-routing-resolve.service';

const indicatorRoute: Routes = [
  {
    path: '',
    component: IndicatorComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicatorDetailComponent,
    resolve: {
      indicator: IndicatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicatorUpdateComponent,
    resolve: {
      indicator: IndicatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicatorUpdateComponent,
    resolve: {
      indicator: IndicatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default indicatorRoute;
