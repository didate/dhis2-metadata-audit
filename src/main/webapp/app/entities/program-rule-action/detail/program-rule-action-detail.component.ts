import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProgramRuleAction } from '../program-rule-action.model';

@Component({
  standalone: true,
  selector: 'jhi-program-rule-action-detail',
  templateUrl: './program-rule-action-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProgramRuleActionDetailComponent {
  programRuleAction = input<IProgramRuleAction | null>(null);

  previousState(): void {
    window.history.back();
  }
}
