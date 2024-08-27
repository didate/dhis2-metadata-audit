import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramRuleVariableComponent } from './list/program-rule-variable.component';
import { ProgramRuleVariableDetailComponent } from './detail/program-rule-variable-detail.component';
import { ProgramRuleVariableRoutingModule } from './route/program-rule-variable-routing.module';

@NgModule({
  imports: [SharedModule, ProgramRuleVariableRoutingModule],
  declarations: [ProgramRuleVariableComponent, ProgramRuleVariableDetailComponent],
})
export class ProgramRuleVariableModule {}
