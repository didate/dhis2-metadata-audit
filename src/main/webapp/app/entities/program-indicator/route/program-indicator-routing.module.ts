import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramIndicatorComponent } from '../list/program-indicator.component';
import { ProgramIndicatorDetailComponent } from '../detail/program-indicator-detail.component';
import { ProgramIndicatorUpdateComponent } from '../update/program-indicator-update.component';
import { ProgramIndicatorRoutingResolveService } from './program-indicator-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const programIndicatorRoute: Routes = [
  {
    path: '',
    component: ProgramIndicatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
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
  {
    path: 'new',
    component: ProgramIndicatorUpdateComponent,
    resolve: {
      programIndicator: ProgramIndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramIndicatorUpdateComponent,
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