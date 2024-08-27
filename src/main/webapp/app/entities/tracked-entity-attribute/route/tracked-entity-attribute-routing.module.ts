import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrackedEntityAttributeComponent } from '../list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from '../detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeRoutingResolveService } from './tracked-entity-attribute-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';

const trackedEntityAttributeRoute: Routes = [
  {
    path: '',
    component: TrackedEntityAttributeComponent,
    data: {
      defaultSort: 'lastUpdated,' + DESC,
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
];

@NgModule({
  imports: [RouterModule.forChild(trackedEntityAttributeRoute)],
  exports: [RouterModule],
})
export class TrackedEntityAttributeRoutingModule {}
