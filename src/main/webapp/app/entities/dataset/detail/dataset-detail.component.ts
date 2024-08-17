import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDataset } from '../dataset.model';

@Component({
  standalone: true,
  selector: 'jhi-dataset-detail',
  templateUrl: './dataset-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DatasetDetailComponent {
  dataset = input<IDataset | null>(null);

  previousState(): void {
    window.history.back();
  }
}
