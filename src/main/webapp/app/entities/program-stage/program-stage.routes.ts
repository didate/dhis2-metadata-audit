import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramStageComponent } from './list/program-stage.component';
import { ProgramStageDetailComponent } from './detail/program-stage-detail.component';
import { ProgramStageUpdateComponent } from './update/program-stage-update.component';
import ProgramStageResolve from './route/program-stage-routing-resolve.service';

const programStageRoute: Routes = [
  {
    path: '',
    component: ProgramStageComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramStageDetailComponent,
    resolve: {
      programStage: ProgramStageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramStageUpdateComponent,
    resolve: {
      programStage: ProgramStageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramStageUpdateComponent,
    resolve: {
      programStage: ProgramStageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default programStageRoute;
