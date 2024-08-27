import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProgramRuleComponent } from '../list/program-rule.component';
import { ProgramRuleDetailComponent } from '../detail/program-rule-detail.component';
import { ProgramRuleRoutingResolveService } from './program-rule-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const programRuleRoute: Routes = [
  {
    path: '',
    component: ProgramRuleComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
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
];

@NgModule({
  imports: [RouterModule.forChild(programRuleRoute)],
  exports: [RouterModule],
})
export class ProgramRuleRoutingModule {}
