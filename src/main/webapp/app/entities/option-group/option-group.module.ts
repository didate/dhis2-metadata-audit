import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OptionGroupComponent } from './list/option-group.component';
import { OptionGroupDetailComponent } from './detail/option-group-detail.component';
import { OptionGroupUpdateComponent } from './update/option-group-update.component';
import { OptionGroupDeleteDialogComponent } from './delete/option-group-delete-dialog.component';
import { OptionGroupRoutingModule } from './route/option-group-routing.module';

@NgModule({
  imports: [SharedModule, OptionGroupRoutingModule],
  declarations: [OptionGroupComponent, OptionGroupDetailComponent, OptionGroupUpdateComponent, OptionGroupDeleteDialogComponent],
})
export class OptionGroupModule {}
