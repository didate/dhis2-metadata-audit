import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IndicatortypeComponent } from './list/indicatortype.component';
import { IndicatortypeDetailComponent } from './detail/indicatortype-detail.component';
import { IndicatortypeUpdateComponent } from './update/indicatortype-update.component';
import { IndicatortypeDeleteDialogComponent } from './delete/indicatortype-delete-dialog.component';
import { IndicatortypeRoutingModule } from './route/indicatortype-routing.module';

@NgModule({
  imports: [SharedModule, IndicatortypeRoutingModule],
  declarations: [IndicatortypeComponent, IndicatortypeDetailComponent, IndicatortypeUpdateComponent, IndicatortypeDeleteDialogComponent],
})
export class IndicatortypeModule {}
