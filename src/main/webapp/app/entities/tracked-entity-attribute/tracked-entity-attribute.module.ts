import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrackedEntityAttributeComponent } from './list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from './detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeRoutingModule } from './route/tracked-entity-attribute-routing.module';
import { TrackedEntityAttributeHistoryComponent } from './history/tracked-entity-attribute-history.component';

@NgModule({
  imports: [SharedModule, TrackedEntityAttributeRoutingModule],
  declarations: [TrackedEntityAttributeComponent, TrackedEntityAttributeDetailComponent, TrackedEntityAttributeHistoryComponent],
})
export class TrackedEntityAttributeModule {}
