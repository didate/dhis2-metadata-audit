import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OptionGroupComponent } from './list/option-group.component';
import { OptionGroupDetailComponent } from './detail/option-group-detail.component';
import { OptionGroupRoutingModule } from './route/option-group-routing.module';
import { OptionGroupHistoryComponent } from './history/option-group-history.component';

@NgModule({
  imports: [SharedModule, OptionGroupRoutingModule],
  declarations: [OptionGroupComponent, OptionGroupDetailComponent, OptionGroupHistoryComponent],
})
export class OptionGroupModule {}
