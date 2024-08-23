import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PersonNotifierComponent } from './list/person-notifier.component';
import { PersonNotifierDetailComponent } from './detail/person-notifier-detail.component';
import { PersonNotifierUpdateComponent } from './update/person-notifier-update.component';
import { PersonNotifierDeleteDialogComponent } from './delete/person-notifier-delete-dialog.component';
import { PersonNotifierRoutingModule } from './route/person-notifier-routing.module';

@NgModule({
  imports: [SharedModule, PersonNotifierRoutingModule],
  declarations: [
    PersonNotifierComponent,
    PersonNotifierDetailComponent,
    PersonNotifierUpdateComponent,
    PersonNotifierDeleteDialogComponent,
  ],
})
export class PersonNotifierModule {}
