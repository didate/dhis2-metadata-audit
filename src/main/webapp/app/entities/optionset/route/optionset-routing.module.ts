import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OptionsetComponent } from '../list/optionset.component';
import { OptionsetDetailComponent } from '../detail/optionset-detail.component';
import { OptionsetRoutingResolveService } from './optionset-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { OptionsetHistoryComponent } from '../history/optionset-history.component';

const optionsetRoute: Routes = [
  {
    path: '',
    component: OptionsetComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/compare/:rev1/:rev2',
    component: OptionsetDetailComponent,
    resolve: {
      optionset: OptionsetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: OptionsetHistoryComponent,
    resolve: {
      program: OptionsetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(optionsetRoute)],
  exports: [RouterModule],
})
export class OptionsetRoutingModule {}
