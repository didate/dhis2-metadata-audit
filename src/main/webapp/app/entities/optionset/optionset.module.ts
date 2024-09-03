import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OptionsetComponent } from './list/optionset.component';
import { OptionsetDetailComponent } from './detail/optionset-detail.component';
import { OptionsetRoutingModule } from './route/optionset-routing.module';
import { OptionsetHistoryComponent } from './history/optionset-history.component';

@NgModule({
  imports: [SharedModule, OptionsetRoutingModule],
  declarations: [OptionsetComponent, OptionsetDetailComponent, OptionsetHistoryComponent],
})
export class OptionsetModule {}
