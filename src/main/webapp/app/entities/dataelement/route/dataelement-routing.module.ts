import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataelementComponent } from '../list/dataelement.component';
import { DataelementDetailComponent } from '../detail/dataelement-detail.component';
import { DataelementRoutingResolveService } from './dataelement-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { DataelementHistoryComponent } from '../history/dataelement-history.component';

const dataelementRoute: Routes = [
  {
    path: '',
    component: DataelementComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/compare/:rev1/:rev2',
    component: DataelementDetailComponent,
    resolve: {
      dataelement: DataelementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: DataelementHistoryComponent,
    resolve: {
      dataelement: DataelementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dataelementRoute)],
  exports: [RouterModule],
})
export class DataelementRoutingModule {}
