import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OptionsetComponent } from './list/optionset.component';
import { OptionsetDetailComponent } from './detail/optionset-detail.component';
import { OptionsetRoutingModule } from './route/optionset-routing.module';

@NgModule({
  imports: [SharedModule, OptionsetRoutingModule],
  declarations: [OptionsetComponent, OptionsetDetailComponent],
})
export class OptionsetModule {}
