import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramComponent } from '../list/program.component';
import { ProgramDetailComponent } from '../detail/program-detail.component';
import { ProgramRoutingResolveService } from './program-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { ProgramHistoryComponent } from '../history/program-history.component';

const programRoute: Routes = [
  {
    path: '',
    component: ProgramComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramDetailComponent,
    resolve: {
      program: ProgramRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/compare/:rev1/:rev2',
    component: ProgramDetailComponent,
    resolve: {
      program: ProgramRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: ProgramHistoryComponent,
    resolve: {
      program: ProgramRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programRoute)],
  exports: [RouterModule],
})
export class ProgramRoutingModule {}
