import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './program-rule-variable-delete-dialog.component.html',
})
export class ProgramRuleVariableDeleteDialogComponent {
  programRuleVariable?: IProgramRuleVariable;

  constructor(protected programRuleVariableService: ProgramRuleVariableService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programRuleVariableService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
