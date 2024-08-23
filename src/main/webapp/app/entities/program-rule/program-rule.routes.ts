import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleComponent } from './list/program-rule.component';
import { ProgramRuleDetailComponent } from './detail/program-rule-detail.component';
import { ProgramRuleUpdateComponent } from './update/program-rule-update.component';
import ProgramRuleResolve from './route/program-rule-routing-resolve.service';

const programRuleRoute: Routes = [
  {
    path: '',
    component: ProgramRuleComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramRuleDetailComponent,
    resolve: {
      programRule: ProgramRuleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramRuleUpdateComponent,
    resolve: {
      programRule: ProgramRuleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramRuleUpdateComponent,
    resolve: {
      programRule: ProgramRuleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default programRuleRoute;
