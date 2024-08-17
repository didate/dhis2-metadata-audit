import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDataelement } from '../dataelement.model';

@Component({
  standalone: true,
  selector: 'jhi-dataelement-detail',
  templateUrl: './dataelement-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DataelementDetailComponent {
  dataelement = input<IDataelement | null>(null);

  previousState(): void {
    window.history.back();
  }
}
