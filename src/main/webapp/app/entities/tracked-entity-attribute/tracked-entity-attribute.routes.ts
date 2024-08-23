import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrackedEntityAttributeComponent } from './list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from './detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeUpdateComponent } from './update/tracked-entity-attribute-update.component';
import TrackedEntityAttributeResolve from './route/tracked-entity-attribute-routing-resolve.service';

const trackedEntityAttributeRoute: Routes = [
  {
    path: '',
    component: TrackedEntityAttributeComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrackedEntityAttributeDetailComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrackedEntityAttributeUpdateComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrackedEntityAttributeUpdateComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default trackedEntityAttributeRoute;
