import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DHISUserComponent } from '../list/dhis-user.component';
import { DHISUserDetailComponent } from '../detail/dhis-user-detail.component';
import { DHISUserRoutingResolveService } from './dhis-user-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { DhisUserHistoryComponent } from '../history/dhis-user-history.component';

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
    path: ':historyId/compare/:rev1/:rev2',
    component: DHISUserDetailComponent,
    resolve: {
      dHISUser: DHISUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: DhisUserHistoryComponent,
    resolve: {
      program: DHISUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dHISUserRoute)],
  exports: [RouterModule],
})
export class DHISUserRoutingModule {}
