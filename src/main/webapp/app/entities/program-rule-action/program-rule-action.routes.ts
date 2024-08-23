import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleActionComponent } from './list/program-rule-action.component';
import { ProgramRuleActionDetailComponent } from './detail/program-rule-action-detail.component';
import { ProgramRuleActionUpdateComponent } from './update/program-rule-action-update.component';
import ProgramRuleActionResolve from './route/program-rule-action-routing-resolve.service';

const programRuleActionRoute: Routes = [
  {
    path: '',
    component: ProgramRuleActionComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramRuleActionDetailComponent,
    resolve: {
      programRuleAction: ProgramRuleActionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramRuleActionUpdateComponent,
    resolve: {
      programRuleAction: ProgramRuleActionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramRuleActionUpdateComponent,
    resolve: {
      programRuleAction: ProgramRuleActionResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default programRuleActionRoute;
