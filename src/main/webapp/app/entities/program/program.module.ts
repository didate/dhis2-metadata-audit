import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramComponent } from './list/program.component';
import { ProgramDetailComponent } from './detail/program-detail.component';
import { ProgramRoutingModule } from './route/program-routing.module';

@NgModule({
  imports: [SharedModule, ProgramRoutingModule],
  declarations: [ProgramComponent, ProgramDetailComponent],
})
export class ProgramModule {}
