import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorycomboComponent } from '../list/categorycombo.component';
import { CategorycomboDetailComponent } from '../detail/categorycombo-detail.component';
import { CategorycomboRoutingResolveService } from './categorycombo-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const categorycomboRoute: Routes = [
  {
    path: '',
    component: CategorycomboComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
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
];

@NgModule({
  imports: [RouterModule.forChild(categorycomboRoute)],
  exports: [RouterModule],
})
export class CategorycomboRoutingModule {}
