import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProgramRule } from '../program-rule.model';

@Component({
  standalone: true,
  selector: 'jhi-program-rule-detail',
  templateUrl: './program-rule-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProgramRuleDetailComponent {
  programRule = input<IProgramRule | null>(null);

  previousState(): void {
    window.history.back();
  }
}
