import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DHISUserComponent } from './list/dhis-user.component';
import { DHISUserDetailComponent } from './detail/dhis-user-detail.component';
import { DHISUserUpdateComponent } from './update/dhis-user-update.component';
import DHISUserResolve from './route/dhis-user-routing-resolve.service';

const dHISUserRoute: Routes = [
  {
    path: '',
    component: DHISUserComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DHISUserDetailComponent,
    resolve: {
      dHISUser: DHISUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DHISUserUpdateComponent,
    resolve: {
      dHISUser: DHISUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DHISUserUpdateComponent,
    resolve: {
      dHISUser: DHISUserResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dHISUserRoute;
