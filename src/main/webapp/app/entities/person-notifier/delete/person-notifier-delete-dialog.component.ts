import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonNotifier } from '../person-notifier.model';
import { PersonNotifierService } from '../service/person-notifier.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './person-notifier-delete-dialog.component.html',
})
export class PersonNotifierDeleteDialogComponent {
  personNotifier?: IPersonNotifier;

  constructor(protected personNotifierService: PersonNotifierService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personNotifierService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
