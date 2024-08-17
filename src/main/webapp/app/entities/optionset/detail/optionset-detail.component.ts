import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IOptionset } from '../optionset.model';

@Component({
  standalone: true,
  selector: 'jhi-optionset-detail',
  templateUrl: './optionset-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class OptionsetDetailComponent {
  optionset = input<IOptionset | null>(null);

  previousState(): void {
    window.history.back();
  }
}
