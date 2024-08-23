import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramIndicatorComponent } from './list/program-indicator.component';
import { ProgramIndicatorDetailComponent } from './detail/program-indicator-detail.component';
import { ProgramIndicatorUpdateComponent } from './update/program-indicator-update.component';
import ProgramIndicatorResolve from './route/program-indicator-routing-resolve.service';

const programIndicatorRoute: Routes = [
  {
    path: '',
    component: ProgramIndicatorComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramIndicatorDetailComponent,
    resolve: {
      programIndicator: ProgramIndicatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramIndicatorUpdateComponent,
    resolve: {
      programIndicator: ProgramIndicatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramIndicatorUpdateComponent,
    resolve: {
      programIndicator: ProgramIndicatorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default programIndicatorRoute;
