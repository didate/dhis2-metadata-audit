import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonNotifierComponent } from '../list/person-notifier.component';
import { PersonNotifierDetailComponent } from '../detail/person-notifier-detail.component';
import { PersonNotifierUpdateComponent } from '../update/person-notifier-update.component';
import { PersonNotifierRoutingResolveService } from './person-notifier-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const personNotifierRoute: Routes = [
  {
    path: '',
    component: PersonNotifierComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonNotifierDetailComponent,
    resolve: {
      personNotifier: PersonNotifierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonNotifierUpdateComponent,
    resolve: {
      personNotifier: PersonNotifierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonNotifierUpdateComponent,
    resolve: {
      personNotifier: PersonNotifierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personNotifierRoute)],
  exports: [RouterModule],
})
export class PersonNotifierRoutingModule {}
