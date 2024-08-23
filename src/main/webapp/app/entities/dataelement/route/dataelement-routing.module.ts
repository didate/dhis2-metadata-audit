import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataelementComponent } from '../list/dataelement.component';
import { DataelementDetailComponent } from '../detail/dataelement-detail.component';
import { DataelementUpdateComponent } from '../update/dataelement-update.component';
import { DataelementRoutingResolveService } from './dataelement-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dataelementRoute: Routes = [
  {
    path: '',
    component: DataelementComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataelementDetailComponent,
    resolve: {
      dataelement: DataelementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataelementUpdateComponent,
    resolve: {
      dataelement: DataelementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataelementUpdateComponent,
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
