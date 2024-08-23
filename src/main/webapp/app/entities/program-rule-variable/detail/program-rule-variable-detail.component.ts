import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProgramRuleVariable } from '../program-rule-variable.model';

@Component({
  standalone: true,
  selector: 'jhi-program-rule-variable-detail',
  templateUrl: './program-rule-variable-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProgramRuleVariableDetailComponent {
  programRuleVariable = input<IProgramRuleVariable | null>(null);

  previousState(): void {
    window.history.back();
  }
}
