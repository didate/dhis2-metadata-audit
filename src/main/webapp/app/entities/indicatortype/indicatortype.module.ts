import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IndicatortypeComponent } from './list/indicatortype.component';
import { IndicatortypeDetailComponent } from './detail/indicatortype-detail.component';
import { IndicatortypeRoutingModule } from './route/indicatortype-routing.module';
import { IndicatortypeHistoryComponent } from './history/indicatortype-history.component';

@NgModule({
  imports: [SharedModule, IndicatortypeRoutingModule],
  declarations: [IndicatortypeComponent, IndicatortypeDetailComponent, IndicatortypeHistoryComponent],
})
export class IndicatortypeModule {}
