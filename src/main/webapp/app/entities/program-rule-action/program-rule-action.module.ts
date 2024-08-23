import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramRuleActionComponent } from './list/program-rule-action.component';
import { ProgramRuleActionDetailComponent } from './detail/program-rule-action-detail.component';
import { ProgramRuleActionUpdateComponent } from './update/program-rule-action-update.component';
import { ProgramRuleActionDeleteDialogComponent } from './delete/program-rule-action-delete-dialog.component';
import { ProgramRuleActionRoutingModule } from './route/program-rule-action-routing.module';

@NgModule({
  imports: [SharedModule, ProgramRuleActionRoutingModule],
  declarations: [
    ProgramRuleActionComponent,
    ProgramRuleActionDetailComponent,
    ProgramRuleActionUpdateComponent,
    ProgramRuleActionDeleteDialogComponent,
  ],
})
export class ProgramRuleActionModule {}
