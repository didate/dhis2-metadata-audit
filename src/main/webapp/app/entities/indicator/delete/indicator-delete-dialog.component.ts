import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IIndicator } from '../indicator.model';
import { IndicatorService } from '../service/indicator.service';

@Component({
  standalone: true,
  templateUrl: './indicator-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class IndicatorDeleteDialogComponent {
  indicator?: IIndicator;

  protected indicatorService = inject(IndicatorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.indicatorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
