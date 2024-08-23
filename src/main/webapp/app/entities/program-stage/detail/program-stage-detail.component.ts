import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IProgramStage } from '../program-stage.model';

@Component({
  standalone: true,
  selector: 'jhi-program-stage-detail',
  templateUrl: './program-stage-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ProgramStageDetailComponent {
  programStage = input<IProgramStage | null>(null);

  previousState(): void {
    window.history.back();
  }
}
