import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICategorycombo } from '../categorycombo.model';

@Component({
  standalone: true,
  selector: 'jhi-categorycombo-detail',
  templateUrl: './categorycombo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CategorycomboDetailComponent {
  categorycombo = input<ICategorycombo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
