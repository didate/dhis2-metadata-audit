import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptionset } from '../optionset.model';
import { OptionsetService } from '../service/optionset.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './optionset-delete-dialog.component.html',
})
export class OptionsetDeleteDialogComponent {
  optionset?: IOptionset;

  constructor(protected optionsetService: OptionsetService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.optionsetService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
