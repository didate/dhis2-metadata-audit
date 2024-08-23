import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DatasetComponent } from './list/dataset.component';
import { DatasetDetailComponent } from './detail/dataset-detail.component';
import { DatasetUpdateComponent } from './update/dataset-update.component';
import { DatasetDeleteDialogComponent } from './delete/dataset-delete-dialog.component';
import { DatasetRoutingModule } from './route/dataset-routing.module';

@NgModule({
  imports: [SharedModule, DatasetRoutingModule],
  declarations: [DatasetComponent, DatasetDetailComponent, DatasetUpdateComponent, DatasetDeleteDialogComponent],
})
export class DatasetModule {}
