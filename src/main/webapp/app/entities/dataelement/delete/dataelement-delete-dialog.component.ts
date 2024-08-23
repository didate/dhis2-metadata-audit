import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDataelement } from '../dataelement.model';
import { DataelementService } from '../service/dataelement.service';

@Component({
  standalone: true,
  templateUrl: './dataelement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DataelementDeleteDialogComponent {
  dataelement?: IDataelement;

  protected dataelementService = inject(DataelementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.dataelementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}