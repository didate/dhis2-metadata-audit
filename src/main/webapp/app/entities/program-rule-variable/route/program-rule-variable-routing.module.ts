import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleVariableComponent } from '../list/program-rule-variable.component';
import { ProgramRuleVariableDetailComponent } from '../detail/program-rule-variable-detail.component';
import { ProgramRuleVariableUpdateComponent } from '../update/program-rule-variable-update.component';
import { ProgramRuleVariableRoutingResolveService } from './program-rule-variable-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const programRuleVariableRoute: Routes = [
  {
    path: '',
    component: ProgramRuleVariableComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramRuleVariableDetailComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramRuleVariableUpdateComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramRuleVariableUpdateComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programRuleVariableRoute)],
  exports: [RouterModule],
})
export class ProgramRuleVariableRoutingModule {}
