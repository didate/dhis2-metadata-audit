import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IOrganisationUnit } from '../organisation-unit.model';

@Component({
  standalone: true,
  selector: 'jhi-organisation-unit-detail',
  templateUrl: './organisation-unit-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class OrganisationUnitDetailComponent {
  organisationUnit = input<IOrganisationUnit | null>(null);

  previousState(): void {
    window.history.back();
  }
}
