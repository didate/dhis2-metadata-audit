import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PersonNotifierComponent } from './list/person-notifier.component';
import { PersonNotifierDetailComponent } from './detail/person-notifier-detail.component';
import { PersonNotifierUpdateComponent } from './update/person-notifier-update.component';
import PersonNotifierResolve from './route/person-notifier-routing-resolve.service';

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
      personNotifier: PersonNotifierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonNotifierUpdateComponent,
    resolve: {
      personNotifier: PersonNotifierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonNotifierUpdateComponent,
    resolve: {
      personNotifier: PersonNotifierResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default personNotifierRoute;
