import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProgramStage } from '../program-stage.model';
import { ProgramStageService } from '../service/program-stage.service';

@Component({
  standalone: true,
  templateUrl: './program-stage-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProgramStageDeleteDialogComponent {
  programStage?: IProgramStage;

  protected programStageService = inject(ProgramStageService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programStageService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
