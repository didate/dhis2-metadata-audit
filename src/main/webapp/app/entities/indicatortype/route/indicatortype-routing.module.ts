import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IndicatortypeComponent } from '../list/indicatortype.component';
import { IndicatortypeDetailComponent } from '../detail/indicatortype-detail.component';
import { IndicatortypeRoutingResolveService } from './indicatortype-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const indicatortypeRoute: Routes = [
  {
    path: '',
    component: IndicatortypeComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicatortypeDetailComponent,
    resolve: {
      indicatortype: IndicatortypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(indicatortypeRoute)],
  exports: [RouterModule],
})
export class IndicatortypeRoutingModule {}
