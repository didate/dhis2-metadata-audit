import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';

@Component({
  standalone: true,
  templateUrl: './program-rule-action-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProgramRuleActionDeleteDialogComponent {
  programRuleAction?: IProgramRuleAction;

  protected programRuleActionService = inject(ProgramRuleActionService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programRuleActionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
