import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOptionset } from '../optionset.model';
import { OptionsetService } from '../service/optionset.service';

@Component({
  standalone: true,
  templateUrl: './optionset-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OptionsetDeleteDialogComponent {
  optionset?: IOptionset;

  protected optionsetService = inject(OptionsetService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.optionsetService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
