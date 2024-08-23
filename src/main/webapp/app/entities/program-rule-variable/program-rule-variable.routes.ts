import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleVariableComponent } from './list/program-rule-variable.component';
import { ProgramRuleVariableDetailComponent } from './detail/program-rule-variable-detail.component';
import { ProgramRuleVariableUpdateComponent } from './update/program-rule-variable-update.component';
import ProgramRuleVariableResolve from './route/program-rule-variable-routing-resolve.service';

const programRuleVariableRoute: Routes = [
  {
    path: '',
    component: ProgramRuleVariableComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramRuleVariableDetailComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramRuleVariableUpdateComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramRuleVariableUpdateComponent,
    resolve: {
      programRuleVariable: ProgramRuleVariableResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default programRuleVariableRoute;
