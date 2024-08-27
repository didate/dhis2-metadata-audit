import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DataelementComponent } from './list/dataelement.component';
import { DataelementDetailComponent } from './detail/dataelement-detail.component';
import { DataelementRoutingModule } from './route/dataelement-routing.module';

@NgModule({
  imports: [SharedModule, DataelementRoutingModule],
  declarations: [DataelementComponent, DataelementDetailComponent],
})
export class DataelementModule {}
