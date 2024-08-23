import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramIndicator } from '../program-indicator.model';
import { ProgramIndicatorService } from '../service/program-indicator.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './program-indicator-delete-dialog.component.html',
})
export class ProgramIndicatorDeleteDialogComponent {
  programIndicator?: IProgramIndicator;

  constructor(protected programIndicatorService: ProgramIndicatorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programIndicatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
