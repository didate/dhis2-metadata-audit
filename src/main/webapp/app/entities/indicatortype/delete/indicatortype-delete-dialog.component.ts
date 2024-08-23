import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndicatortype } from '../indicatortype.model';
import { IndicatortypeService } from '../service/indicatortype.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './indicatortype-delete-dialog.component.html',
})
export class IndicatortypeDeleteDialogComponent {
  indicatortype?: IIndicatortype;

  constructor(protected indicatortypeService: IndicatortypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.indicatortypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
