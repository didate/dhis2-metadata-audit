import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICategorycombo } from '../categorycombo.model';
import { CategorycomboService } from '../service/categorycombo.service';

@Component({
  standalone: true,
  templateUrl: './categorycombo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CategorycomboDeleteDialogComponent {
  categorycombo?: ICategorycombo;

  protected categorycomboService = inject(CategorycomboService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.categorycomboService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}