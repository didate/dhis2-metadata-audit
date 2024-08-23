import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './program-rule-delete-dialog.component.html',
})
export class ProgramRuleDeleteDialogComponent {
  programRule?: IProgramRule;

  constructor(protected programRuleService: ProgramRuleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programRuleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
