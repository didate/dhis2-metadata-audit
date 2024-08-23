import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDHISUser } from '../dhis-user.model';
import { DHISUserService } from '../service/dhis-user.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './dhis-user-delete-dialog.component.html',
})
export class DHISUserDeleteDialogComponent {
  dHISUser?: IDHISUser;

  constructor(protected dHISUserService: DHISUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.dHISUserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
