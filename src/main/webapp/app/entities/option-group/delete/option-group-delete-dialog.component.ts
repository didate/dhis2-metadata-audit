import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptionGroup } from '../option-group.model';
import { OptionGroupService } from '../service/option-group.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './option-group-delete-dialog.component.html',
})
export class OptionGroupDeleteDialogComponent {
  optionGroup?: IOptionGroup;

  constructor(protected optionGroupService: OptionGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.optionGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
