import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OptionsetComponent } from './list/optionset.component';
import { OptionsetDetailComponent } from './detail/optionset-detail.component';
import { OptionsetUpdateComponent } from './update/optionset-update.component';
import OptionsetResolve from './route/optionset-routing-resolve.service';

const optionsetRoute: Routes = [
  {
    path: '',
    component: OptionsetComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OptionsetDetailComponent,
    resolve: {
      optionset: OptionsetResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OptionsetUpdateComponent,
    resolve: {
      optionset: OptionsetResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OptionsetUpdateComponent,
    resolve: {
      optionset: OptionsetResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default optionsetRoute;
