import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorycomboComponent } from '../list/categorycombo.component';
import { CategorycomboDetailComponent } from '../detail/categorycombo-detail.component';
import { CategorycomboRoutingResolveService } from './categorycombo-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { CategorycomboHistoryComponent } from '../history/categorycombo-history.component';

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
    path: ':historyId/compare/:rev1/:rev2',
    component: CategorycomboDetailComponent,
    resolve: {
      categorycombo: CategorycomboRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: CategorycomboHistoryComponent,
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
