import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';

@Component({
  standalone: true,
  selector: 'jhi-tracked-entity-attribute-detail',
  templateUrl: './tracked-entity-attribute-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TrackedEntityAttributeDetailComponent {
  trackedEntityAttribute = input<ITrackedEntityAttribute | null>(null);

  previousState(): void {
    window.history.back();
  }
}
