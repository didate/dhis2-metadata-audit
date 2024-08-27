import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OptionGroupComponent } from '../list/option-group.component';
import { OptionGroupDetailComponent } from '../detail/option-group-detail.component';
import { OptionGroupRoutingResolveService } from './option-group-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const optionGroupRoute: Routes = [
  {
    path: '',
    component: OptionGroupComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OptionGroupDetailComponent,
    resolve: {
      optionGroup: OptionGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(optionGroupRoute)],
  exports: [RouterModule],
})
export class OptionGroupRoutingModule {}
