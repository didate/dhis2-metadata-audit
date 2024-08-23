import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategorycomboComponent } from './list/categorycombo.component';
import { CategorycomboDetailComponent } from './detail/categorycombo-detail.component';
import { CategorycomboUpdateComponent } from './update/categorycombo-update.component';
import { CategorycomboDeleteDialogComponent } from './delete/categorycombo-delete-dialog.component';
import { CategorycomboRoutingModule } from './route/categorycombo-routing.module';

@NgModule({
  imports: [SharedModule, CategorycomboRoutingModule],
  declarations: [CategorycomboComponent, CategorycomboDetailComponent, CategorycomboUpdateComponent, CategorycomboDeleteDialogComponent],
})
export class CategorycomboModule {}
