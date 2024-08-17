import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorycomboComponent } from './list/categorycombo.component';
import { CategorycomboDetailComponent } from './detail/categorycombo-detail.component';
import { CategorycomboUpdateComponent } from './update/categorycombo-update.component';
import CategorycomboResolve from './route/categorycombo-routing-resolve.service';

const categorycomboRoute: Routes = [
  {
    path: '',
    component: CategorycomboComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorycomboDetailComponent,
    resolve: {
      categorycombo: CategorycomboResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorycomboUpdateComponent,
    resolve: {
      categorycombo: CategorycomboResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorycomboUpdateComponent,
    resolve: {
      categorycombo: CategorycomboResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categorycomboRoute;
