import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';

@Component({
  standalone: true,
  templateUrl: './program-rule-variable-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProgramRuleVariableDeleteDialogComponent {
  programRuleVariable?: IProgramRuleVariable;

  protected programRuleVariableService = inject(ProgramRuleVariableService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programRuleVariableService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
