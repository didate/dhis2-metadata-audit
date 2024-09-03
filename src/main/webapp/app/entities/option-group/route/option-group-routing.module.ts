import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OptionGroupComponent } from '../list/option-group.component';
import { OptionGroupDetailComponent } from '../detail/option-group-detail.component';
import { OptionGroupRoutingResolveService } from './option-group-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { OptionGroupHistoryComponent } from '../history/option-group-history.component';

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
    path: ':historyId/compare/:rev1/:rev2',
    component: OptionGroupDetailComponent,
    resolve: {
      optionGroup: OptionGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: OptionGroupHistoryComponent,
    resolve: {
      program: OptionGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(optionGroupRoute)],
  exports: [RouterModule],
})
export class OptionGroupRoutingModule {}
