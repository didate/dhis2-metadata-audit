import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramStageComponent } from '../list/program-stage.component';
import { ProgramStageDetailComponent } from '../detail/program-stage-detail.component';
import { ProgramStageRoutingResolveService } from './program-stage-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { ProgramStageHistoryComponent } from '../history/program-stage-history.component';

const programStageRoute: Routes = [
  {
    path: '',
    component: ProgramStageComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/compare/:rev1/:rev2',
    component: ProgramStageDetailComponent,
    resolve: {
      programStage: ProgramStageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: ProgramStageHistoryComponent,
    resolve: {
      programStage: ProgramStageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programStageRoute)],
  exports: [RouterModule],
})
export class ProgramStageRoutingModule {}
