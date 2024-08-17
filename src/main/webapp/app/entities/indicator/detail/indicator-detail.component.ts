import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IIndicator } from '../indicator.model';

@Component({
  standalone: true,
  selector: 'jhi-indicator-detail',
  templateUrl: './indicator-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class IndicatorDetailComponent {
  indicator = input<IIndicator | null>(null);

  previousState(): void {
    window.history.back();
  }
}
