import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramIndicatorComponent } from '../list/program-indicator.component';
import { ProgramIndicatorDetailComponent } from '../detail/program-indicator-detail.component';
import { ProgramIndicatorRoutingResolveService } from './program-indicator-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const programIndicatorRoute: Routes = [
  {
    path: '',
    component: ProgramIndicatorComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramIndicatorDetailComponent,
    resolve: {
      programIndicator: ProgramIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programIndicatorRoute)],
  exports: [RouterModule],
})
export class ProgramIndicatorRoutingModule {}
