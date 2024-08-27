import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DHISUserComponent } from './list/dhis-user.component';
import { DHISUserDetailComponent } from './detail/dhis-user-detail.component';
import { DHISUserRoutingModule } from './route/dhis-user-routing.module';

@NgModule({
  imports: [SharedModule, DHISUserRoutingModule],
  declarations: [DHISUserComponent, DHISUserDetailComponent],
})
export class DHISUserModule {}
