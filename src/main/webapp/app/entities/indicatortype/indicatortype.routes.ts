import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IndicatortypeComponent } from './list/indicatortype.component';
import { IndicatortypeDetailComponent } from './detail/indicatortype-detail.component';
import { IndicatortypeUpdateComponent } from './update/indicatortype-update.component';
import IndicatortypeResolve from './route/indicatortype-routing-resolve.service';

const indicatortypeRoute: Routes = [
  {
    path: '',
    component: IndicatortypeComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicatortypeDetailComponent,
    resolve: {
      indicatortype: IndicatortypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicatortypeUpdateComponent,
    resolve: {
      indicatortype: IndicatortypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicatortypeUpdateComponent,
    resolve: {
      indicatortype: IndicatortypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default indicatortypeRoute;
