import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProgramRuleComponent } from './list/program-rule.component';
import { ProgramRuleDetailComponent } from './detail/program-rule-detail.component';
import { ProgramRuleRoutingModule } from './route/program-rule-routing.module';
import { ProgramRuleHistoryComponent } from './history/program-rule-history.component';

@NgModule({
  imports: [SharedModule, ProgramRuleRoutingModule],
  declarations: [ProgramRuleComponent, ProgramRuleDetailComponent, ProgramRuleHistoryComponent],
})
export class ProgramRuleModule {}
