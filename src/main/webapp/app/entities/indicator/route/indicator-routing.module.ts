import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IndicatorComponent } from '../list/indicator.component';
import { IndicatorDetailComponent } from '../detail/indicator-detail.component';
import { IndicatorUpdateComponent } from '../update/indicator-update.component';
import { IndicatorRoutingResolveService } from './indicator-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const indicatorRoute: Routes = [
  {
    path: '',
    component: IndicatorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicatorDetailComponent,
    resolve: {
      indicator: IndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicatorUpdateComponent,
    resolve: {
      indicator: IndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicatorUpdateComponent,
    resolve: {
      indicator: IndicatorRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(indicatorRoute)],
  exports: [RouterModule],
})
export class IndicatorRoutingModule {}