import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IndicatorComponent } from '../list/indicator.component';
import { IndicatorDetailComponent } from '../detail/indicator-detail.component';
import { IndicatorRoutingResolveService } from './indicator-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { IndicatortypeHistoryComponent } from 'app/entities/indicatortype/history/indicatortype-history.component';

const indicatorRoute: Routes = [
  {
    path: '',
    component: IndicatorComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/compare/:rev1/:rev2',
    component: IndicatorDetailComponent,
    resolve: {
      indicator: IndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: IndicatortypeHistoryComponent,
    resolve: {
      program: IndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(indicatorRoute)],
  exports: [RouterModule],
})
export class IndicatorRoutingModule {}
