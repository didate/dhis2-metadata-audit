import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrackedEntityAttributeComponent } from '../list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from '../detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeUpdateComponent } from '../update/tracked-entity-attribute-update.component';
import { TrackedEntityAttributeRoutingResolveService } from './tracked-entity-attribute-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trackedEntityAttributeRoute: Routes = [
  {
    path: '',
    component: TrackedEntityAttributeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrackedEntityAttributeDetailComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrackedEntityAttributeUpdateComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrackedEntityAttributeUpdateComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trackedEntityAttributeRoute)],
  exports: [RouterModule],
})
export class TrackedEntityAttributeRoutingModule {}
