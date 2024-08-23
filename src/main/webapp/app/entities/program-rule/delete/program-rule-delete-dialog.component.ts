import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';

@Component({
  standalone: true,
  templateUrl: './program-rule-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ProgramRuleDeleteDialogComponent {
  programRule?: IProgramRule;

  protected programRuleService = inject(ProgramRuleService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.programRuleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
