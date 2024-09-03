import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleVariableComponent } from '../list/program-rule-variable.component';
import { ProgramRuleVariableDetailComponent } from '../detail/program-rule-variable-detail.component';
import { ProgramRuleVariableRoutingResolveService } from './program-rule-variable-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { ProgramRuleVariableHistoryComponent } from '../history/program-rule-variable-history.component';

const programRuleVariableRoute: Routes = [
  {
    path: '',
    component: ProgramRuleVariableComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/compare/:rev1/:rev2',
    component: ProgramRuleVariableDetailComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: ProgramRuleVariableHistoryComponent,
    resolve: {
      program: ProgramRuleVariableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programRuleVariableRoute)],
  exports: [RouterModule],
})
export class ProgramRuleVariableRoutingModule {}
