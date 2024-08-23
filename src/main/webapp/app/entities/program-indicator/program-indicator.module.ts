import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramIndicatorComponent } from './list/program-indicator.component';
import { ProgramIndicatorDetailComponent } from './detail/program-indicator-detail.component';
import { ProgramIndicatorUpdateComponent } from './update/program-indicator-update.component';
import { ProgramIndicatorDeleteDialogComponent } from './delete/program-indicator-delete-dialog.component';
import { ProgramIndicatorRoutingModule } from './route/program-indicator-routing.module';

@NgModule({
  imports: [SharedModule, ProgramIndicatorRoutingModule],
  declarations: [
    ProgramIndicatorComponent,
    ProgramIndicatorDetailComponent,
    ProgramIndicatorUpdateComponent,
    ProgramIndicatorDeleteDialogComponent,
  ],
})
export class ProgramIndicatorModule {}