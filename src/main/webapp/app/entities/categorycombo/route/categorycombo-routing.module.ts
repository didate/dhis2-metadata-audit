import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorycomboComponent } from '../list/categorycombo.component';
import { CategorycomboDetailComponent } from '../detail/categorycombo-detail.component';
import { CategorycomboUpdateComponent } from '../update/categorycombo-update.component';
import { CategorycomboRoutingResolveService } from './categorycombo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categorycomboRoute: Routes = [
  {
    path: '',
    component: CategorycomboComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorycomboDetailComponent,
    resolve: {
      categorycombo: CategorycomboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorycomboUpdateComponent,
    resolve: {
      categorycombo: CategorycomboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorycomboUpdateComponent,
    resolve: {
      categorycombo: CategorycomboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorycomboRoute)],
  exports: [RouterModule],
})
export class CategorycomboRoutingModule {}
