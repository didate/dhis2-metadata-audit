import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DatasetComponent } from '../list/dataset.component';
import { DatasetDetailComponent } from '../detail/dataset-detail.component';
import { DatasetRoutingResolveService } from './dataset-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { DatasetHistoryComponent } from '../history/dataset-history.component';

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
    path: ':historyId/compare/:rev1/:rev2',
    component: DatasetDetailComponent,
    resolve: {
      dataset: DatasetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: DatasetHistoryComponent,
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
