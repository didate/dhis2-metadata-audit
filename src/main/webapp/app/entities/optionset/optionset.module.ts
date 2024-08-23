import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OptionsetComponent } from './list/optionset.component';
import { OptionsetDetailComponent } from './detail/optionset-detail.component';
import { OptionsetUpdateComponent } from './update/optionset-update.component';
import { OptionsetDeleteDialogComponent } from './delete/optionset-delete-dialog.component';
import { OptionsetRoutingModule } from './route/optionset-routing.module';

@NgModule({
  imports: [SharedModule, OptionsetRoutingModule],
  declarations: [OptionsetComponent, OptionsetDetailComponent, OptionsetUpdateComponent, OptionsetDeleteDialogComponent],
})
export class OptionsetModule {}
