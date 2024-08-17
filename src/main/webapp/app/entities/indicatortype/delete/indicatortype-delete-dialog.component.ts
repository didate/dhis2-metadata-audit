import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIndicatortype } from '../indicatortype.model';
import { IndicatortypeService } from '../service/indicatortype.service';

@Component({
  standalone: true,
  templateUrl: './indicatortype-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IndicatortypeDeleteDialogComponent {
  indicatortype?: IIndicatortype;

  protected indicatortypeService = inject(IndicatortypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.indicatortypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
