import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DatasetComponent } from './list/dataset.component';
import { DatasetDetailComponent } from './detail/dataset-detail.component';
import { DatasetUpdateComponent } from './update/dataset-update.component';
import DatasetResolve from './route/dataset-routing-resolve.service';

const datasetRoute: Routes = [
  {
    path: '',
    component: DatasetComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DatasetDetailComponent,
    resolve: {
      dataset: DatasetResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DatasetUpdateComponent,
    resolve: {
      dataset: DatasetResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DatasetUpdateComponent,
    resolve: {
      dataset: DatasetResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default datasetRoute;
