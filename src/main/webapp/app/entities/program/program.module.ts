import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramComponent } from './list/program.component';
import { ProgramDetailComponent } from './detail/program-detail.component';
import { ProgramRoutingModule } from './route/program-routing.module';
import { ProgramHistoryComponent } from './history/program-history.component';

@NgModule({
  imports: [SharedModule, ProgramRoutingModule],
  declarations: [ProgramComponent, ProgramDetailComponent, ProgramHistoryComponent],
})
export class ProgramModule {}
