import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DatasetComponent } from './list/dataset.component';
import { DatasetDetailComponent } from './detail/dataset-detail.component';
import { DatasetRoutingModule } from './route/dataset-routing.module';
import { DatasetHistoryComponent } from './history/dataset-history.component';

@NgModule({
  imports: [SharedModule, DatasetRoutingModule],
  declarations: [DatasetComponent, DatasetDetailComponent, DatasetHistoryComponent],
})
export class DatasetModule {}
