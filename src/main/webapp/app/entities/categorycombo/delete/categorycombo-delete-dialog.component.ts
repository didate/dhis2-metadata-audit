import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorycombo } from '../categorycombo.model';
import { CategorycomboService } from '../service/categorycombo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './categorycombo-delete-dialog.component.html',
})
export class CategorycomboDeleteDialogComponent {
  categorycombo?: ICategorycombo;

  constructor(protected categorycomboService: CategorycomboService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.categorycomboService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
