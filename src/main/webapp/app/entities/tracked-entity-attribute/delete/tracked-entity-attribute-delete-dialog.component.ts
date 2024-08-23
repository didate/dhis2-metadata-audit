import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';

@Component({
  standalone: true,
  templateUrl: './tracked-entity-attribute-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TrackedEntityAttributeDeleteDialogComponent {
  trackedEntityAttribute?: ITrackedEntityAttribute;

  protected trackedEntityAttributeService = inject(TrackedEntityAttributeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.trackedEntityAttributeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
