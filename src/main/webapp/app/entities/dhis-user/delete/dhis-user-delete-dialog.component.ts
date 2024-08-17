import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDHISUser } from '../dhis-user.model';
import { DHISUserService } from '../service/dhis-user.service';

@Component({
  standalone: true,
  templateUrl: './dhis-user-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DHISUserDeleteDialogComponent {
  dHISUser?: IDHISUser;

  protected dHISUserService = inject(DHISUserService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.dHISUserService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
