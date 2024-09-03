import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramRuleActionComponent } from './list/program-rule-action.component';
import { ProgramRuleActionDetailComponent } from './detail/program-rule-action-detail.component';
import { ProgramRuleActionRoutingModule } from './route/program-rule-action-routing.module';
import { ProgramRuleActionHistoryComponent } from './history/program-rule-action-history.component';

@NgModule({
  imports: [SharedModule, ProgramRuleActionRoutingModule],
  declarations: [ProgramRuleActionComponent, ProgramRuleActionDetailComponent, ProgramRuleActionHistoryComponent],
})
export class ProgramRuleActionModule {}
