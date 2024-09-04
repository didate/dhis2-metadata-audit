import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IndicatorComponent } from './list/indicator.component';
import { IndicatorDetailComponent } from './detail/indicator-detail.component';
import { IndicatorRoutingModule } from './route/indicator-routing.module';
import { IndicatorHistoryComponent } from './history/indicator-history.component';

@NgModule({
  imports: [SharedModule, IndicatorRoutingModule],
  declarations: [IndicatorComponent, IndicatorDetailComponent, IndicatorHistoryComponent],
})
export class IndicatorModule {}
