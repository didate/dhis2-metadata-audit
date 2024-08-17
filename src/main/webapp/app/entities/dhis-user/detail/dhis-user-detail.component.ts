import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDHISUser } from '../dhis-user.model';

@Component({
  standalone: true,
  selector: 'jhi-dhis-user-detail',
  templateUrl: './dhis-user-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DHISUserDetailComponent {
  dHISUser = input<IDHISUser | null>(null);

  previousState(): void {
    window.history.back();
  }
}
