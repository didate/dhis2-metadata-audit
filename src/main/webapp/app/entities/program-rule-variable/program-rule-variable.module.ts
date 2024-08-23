import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramRuleVariableComponent } from './list/program-rule-variable.component';
import { ProgramRuleVariableDetailComponent } from './detail/program-rule-variable-detail.component';
import { ProgramRuleVariableUpdateComponent } from './update/program-rule-variable-update.component';
import { ProgramRuleVariableDeleteDialogComponent } from './delete/program-rule-variable-delete-dialog.component';
import { ProgramRuleVariableRoutingModule } from './route/program-rule-variable-routing.module';

@NgModule({
  imports: [SharedModule, ProgramRuleVariableRoutingModule],
  declarations: [
    ProgramRuleVariableComponent,
    ProgramRuleVariableDetailComponent,
    ProgramRuleVariableUpdateComponent,
    ProgramRuleVariableDeleteDialogComponent,
  ],
})
export class ProgramRuleVariableModule {}
