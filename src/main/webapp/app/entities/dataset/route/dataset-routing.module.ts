import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DatasetComponent } from '../list/dataset.component';
import { DatasetDetailComponent } from '../detail/dataset-detail.component';
import { DatasetUpdateComponent } from '../update/dataset-update.component';
import { DatasetRoutingResolveService } from './dataset-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const datasetRoute: Routes = [
  {
    path: '',
    component: DatasetComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetDetailComponent,
    resolve: {
      dataset: DatasetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetUpdateComponent,
    resolve: {
      dataset: DatasetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetUpdateComponent,
    resolve: {
      dataset: DatasetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(datasetRoute)],
  exports: [RouterModule],
})
export class DatasetRoutingModule {}
