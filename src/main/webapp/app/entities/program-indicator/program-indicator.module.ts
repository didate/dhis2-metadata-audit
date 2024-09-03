import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramIndicatorComponent } from './list/program-indicator.component';
import { ProgramIndicatorDetailComponent } from './detail/program-indicator-detail.component';
import { ProgramIndicatorRoutingModule } from './route/program-indicator-routing.module';
import { ProgramIndicatorHistoryComponent } from './history/program-indicator-history.component';

@NgModule({
  imports: [SharedModule, ProgramIndicatorRoutingModule],
  declarations: [ProgramIndicatorComponent, ProgramIndicatorDetailComponent, ProgramIndicatorHistoryComponent],
})
export class ProgramIndicatorModule {}
