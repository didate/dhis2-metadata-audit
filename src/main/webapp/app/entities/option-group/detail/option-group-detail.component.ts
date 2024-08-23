import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IOptionGroup } from '../option-group.model';

@Component({
  standalone: true,
  selector: 'jhi-option-group-detail',
  templateUrl: './option-group-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class OptionGroupDetailComponent {
  optionGroup = input<IOptionGroup | null>(null);

  previousState(): void {
    window.history.back();
  }
}
