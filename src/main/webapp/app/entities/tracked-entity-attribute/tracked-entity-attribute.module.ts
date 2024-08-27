import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrackedEntityAttributeComponent } from './list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from './detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeRoutingModule } from './route/tracked-entity-attribute-routing.module';

@NgModule({
  imports: [SharedModule, TrackedEntityAttributeRoutingModule],
  declarations: [TrackedEntityAttributeComponent, TrackedEntityAttributeDetailComponent],
})
export class TrackedEntityAttributeModule {}
