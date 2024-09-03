import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DataelementComponent } from './list/dataelement.component';
import { DataelementDetailComponent } from './detail/dataelement-detail.component';
import { DataelementRoutingModule } from './route/dataelement-routing.module';
import { DataelementHistoryComponent } from './history/dataelement-history.component';

@NgModule({
  imports: [SharedModule, DataelementRoutingModule],
  declarations: [DataelementComponent, DataelementDetailComponent, DataelementHistoryComponent],
})
export class DataelementModule {}
