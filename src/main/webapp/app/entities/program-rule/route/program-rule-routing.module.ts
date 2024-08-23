import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleComponent } from '../list/program-rule.component';
import { ProgramRuleDetailComponent } from '../detail/program-rule-detail.component';
import { ProgramRuleUpdateComponent } from '../update/program-rule-update.component';
import { ProgramRuleRoutingResolveService } from './program-rule-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const programRuleRoute: Routes = [
  {
    path: '',
    component: ProgramRuleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProgramRuleDetailComponent,
    resolve: {
      programRule: ProgramRuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProgramRuleUpdateComponent,
    resolve: {
      programRule: ProgramRuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProgramRuleUpdateComponent,
    resolve: {
      programRule: ProgramRuleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(programRuleRoute)],
  exports: [RouterModule],
})
export class ProgramRuleRoutingModule {}
