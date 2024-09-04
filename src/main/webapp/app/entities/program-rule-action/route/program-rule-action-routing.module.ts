import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleActionComponent } from '../list/program-rule-action.component';
import { ProgramRuleActionDetailComponent } from '../detail/program-rule-action-detail.component';
import { ProgramRuleActionRoutingResolveService } from './program-rule-action-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { ProgramRuleActionHistoryComponent } from '../history/program-rule-action-history.component';

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
    path: ':historyId/compare/:rev1/:rev2',
    component: ProgramRuleActionDetailComponent,
    resolve: {
      programRuleAction: ProgramRuleActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: ProgramRuleActionHistoryComponent,
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
