import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrackedEntityAttributeComponent } from './list/tracked-entity-attribute.component';
import { TrackedEntityAttributeDetailComponent } from './detail/tracked-entity-attribute-detail.component';
import { TrackedEntityAttributeUpdateComponent } from './update/tracked-entity-attribute-update.component';
import { TrackedEntityAttributeDeleteDialogComponent } from './delete/tracked-entity-attribute-delete-dialog.component';
import { TrackedEntityAttributeRoutingModule } from './route/tracked-entity-attribute-routing.module';

@NgModule({
  imports: [SharedModule, TrackedEntityAttributeRoutingModule],
  declarations: [
    TrackedEntityAttributeComponent,
    TrackedEntityAttributeDetailComponent,
    TrackedEntityAttributeUpdateComponent,
    TrackedEntityAttributeDeleteDialogComponent,
  ],
})
export class TrackedEntityAttributeModule {}
