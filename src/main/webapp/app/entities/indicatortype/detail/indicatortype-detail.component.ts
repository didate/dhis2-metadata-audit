import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IIndicatortype } from '../indicatortype.model';

@Component({
  standalone: true,
  selector: 'jhi-indicatortype-detail',
  templateUrl: './indicatortype-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class IndicatortypeDetailComponent {
  indicatortype = input<IIndicatortype | null>(null);

  previousState(): void {
    window.history.back();
  }
}
