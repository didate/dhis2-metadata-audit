import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DHISUserComponent } from './list/dhis-user.component';
import { DHISUserDetailComponent } from './detail/dhis-user-detail.component';
import { DHISUserUpdateComponent } from './update/dhis-user-update.component';
import { DHISUserDeleteDialogComponent } from './delete/dhis-user-delete-dialog.component';
import { DHISUserRoutingModule } from './route/dhis-user-routing.module';

@NgModule({
  imports: [SharedModule, DHISUserRoutingModule],
  declarations: [DHISUserComponent, DHISUserDetailComponent, DHISUserUpdateComponent, DHISUserDeleteDialogComponent],
})
export class DHISUserModule {}
