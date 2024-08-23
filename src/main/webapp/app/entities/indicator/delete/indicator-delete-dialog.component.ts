import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndicator } from '../indicator.model';
import { IndicatorService } from '../service/indicator.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './indicator-delete-dialog.component.html',
})
export class IndicatorDeleteDialogComponent {
  indicator?: IIndicator;

  constructor(protected indicatorService: IndicatorService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.indicatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
