import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DatasetComponent } from '../list/dataset.component';
import { DatasetDetailComponent } from '../detail/dataset-detail.component';
import { DatasetRoutingResolveService } from './dataset-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const datasetRoute: Routes = [
  {
    path: '',
    component: DatasetComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
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
];

@NgModule({
  imports: [RouterModule.forChild(datasetRoute)],
  exports: [RouterModule],
})
export class DatasetRoutingModule {}
