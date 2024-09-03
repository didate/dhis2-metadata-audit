import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramStageComponent } from './list/program-stage.component';
import { ProgramStageDetailComponent } from './detail/program-stage-detail.component';
import { ProgramStageRoutingModule } from './route/program-stage-routing.module';
import { ProgramStageHistoryComponent } from './history/program-stage-history.component';

@NgModule({
  imports: [SharedModule, ProgramStageRoutingModule],
  declarations: [ProgramStageComponent, ProgramStageDetailComponent, ProgramStageHistoryComponent],
})
export class ProgramStageModule {}
