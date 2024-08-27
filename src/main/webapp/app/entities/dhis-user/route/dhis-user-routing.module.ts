import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DHISUserComponent } from '../list/dhis-user.component';
import { DHISUserDetailComponent } from '../detail/dhis-user-detail.component';
import { DHISUserUpdateComponent } from '../update/dhis-user-update.component';
import { DHISUserRoutingResolveService } from './dhis-user-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const dHISUserRoute: Routes = [
  {
    path: '',
    component: DHISUserComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DHISUserDetailComponent,
    resolve: {
      dHISUser: DHISUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DHISUserUpdateComponent,
    resolve: {
      dHISUser: DHISUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DHISUserUpdateComponent,
    resolve: {
      dHISUser: DHISUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dHISUserRoute)],
  exports: [RouterModule],
})
export class DHISUserRoutingModule {}
