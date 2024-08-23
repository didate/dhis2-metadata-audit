import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramRuleComponent } from './list/program-rule.component';
import { ProgramRuleDetailComponent } from './detail/program-rule-detail.component';
import { ProgramRuleUpdateComponent } from './update/program-rule-update.component';
import { ProgramRuleDeleteDialogComponent } from './delete/program-rule-delete-dialog.component';
import { ProgramRuleRoutingModule } from './route/program-rule-routing.module';

@NgModule({
  imports: [SharedModule, ProgramRuleRoutingModule],
  declarations: [ProgramRuleComponent, ProgramRuleDetailComponent, ProgramRuleUpdateComponent, ProgramRuleDeleteDialogComponent],
})
export class ProgramRuleModule {}
