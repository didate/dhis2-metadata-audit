import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OptionsetComponent } from '../list/optionset.component';
import { OptionsetDetailComponent } from '../detail/optionset-detail.component';
import { OptionsetRoutingResolveService } from './optionset-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

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
    path: ':id/view',
    component: OptionsetDetailComponent,
    resolve: {
      optionset: OptionsetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(optionsetRoute)],
  exports: [RouterModule],
})
export class OptionsetRoutingModule {}
