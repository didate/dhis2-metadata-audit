import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleActionComponent } from '../list/program-rule-action.component';
import { ProgramRuleActionDetailComponent } from '../detail/program-rule-action-detail.component';
import { ProgramRuleActionUpdateComponent } from '../update/program-rule-action-update.component';
import { ProgramRuleActionRoutingResolveService } from './program-rule-action-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const programRuleActionRoute: Routes = [
  {
    path: '',
    component: ProgramRuleActionComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramRuleActionDetailComponent,
    resolve: {
      programRuleAction: ProgramRuleActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramRuleActionUpdateComponent,
    resolve: {
      programRuleAction: ProgramRuleActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramRuleActionUpdateComponent,
    resolve: {
      programRuleAction: ProgramRuleActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programRuleActionRoute)],
  exports: [RouterModule],
})
export class ProgramRuleActionRoutingModule {}
