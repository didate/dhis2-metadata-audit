import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrackedEntityAttributeComponent } from '../list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from '../detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeRoutingResolveService } from './tracked-entity-attribute-routing-resolve.service';
import { DESC } from 'app/config/navigation.constants';
import { TrackedEntityAttributeHistoryComponent } from '../history/tracked-entity-attribute-history.component';

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
    path: ':historyId/compare/:rev1/:rev2',
    component: TrackedEntityAttributeDetailComponent,
    resolve: {
      trackedEntityAttribute: TrackedEntityAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':historyId/history',
    component: TrackedEntityAttributeHistoryComponent,
    resolve: {
      program: TrackedEntityAttributeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trackedEntityAttributeRoute)],
  exports: [RouterModule],
})
export class TrackedEntityAttributeRoutingModule {}
