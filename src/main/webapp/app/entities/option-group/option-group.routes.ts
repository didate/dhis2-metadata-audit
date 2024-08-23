import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OptionGroupComponent } from './list/option-group.component';
import { OptionGroupDetailComponent } from './detail/option-group-detail.component';
import { OptionGroupUpdateComponent } from './update/option-group-update.component';
import OptionGroupResolve from './route/option-group-routing-resolve.service';

const optionGroupRoute: Routes = [
  {
    path: '',
    component: OptionGroupComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OptionGroupDetailComponent,
    resolve: {
      optionGroup: OptionGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OptionGroupUpdateComponent,
    resolve: {
      optionGroup: OptionGroupResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default optionGroupRoute;
