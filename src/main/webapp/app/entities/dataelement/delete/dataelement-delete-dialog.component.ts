import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDataelement } from '../dataelement.model';
import { DataelementService } from '../service/dataelement.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './dataelement-delete-dialog.component.html',
})
export class DataelementDeleteDialogComponent {
  dataelement?: IDataelement;

  constructor(protected dataelementService: DataelementService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.dataelementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
