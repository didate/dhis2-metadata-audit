import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IOrganisationUnit } from '../organisation-unit.model';
import { OrganisationUnitService } from '../service/organisation-unit.service';

@Component({
  standalone: true,
  templateUrl: './organisation-unit-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class OrganisationUnitDeleteDialogComponent {
  organisationUnit?: IOrganisationUnit;

  protected organisationUnitService = inject(OrganisationUnitService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.organisationUnitService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
