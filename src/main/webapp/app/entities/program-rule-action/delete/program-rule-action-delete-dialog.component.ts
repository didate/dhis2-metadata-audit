import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './program-rule-action-delete-dialog.component.html',
})
export class ProgramRuleActionDeleteDialogComponent {
  programRuleAction?: IProgramRuleAction;

  constructor(protected programRuleActionService: ProgramRuleActionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programRuleActionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
