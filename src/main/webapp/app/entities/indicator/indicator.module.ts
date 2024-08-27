import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IndicatorComponent } from './list/indicator.component';
import { IndicatorDetailComponent } from './detail/indicator-detail.component';
import { IndicatorRoutingModule } from './route/indicator-routing.module';

@NgModule({
  imports: [SharedModule, IndicatorRoutingModule],
  declarations: [IndicatorComponent, IndicatorDetailComponent],
})
export class IndicatorModule {}
