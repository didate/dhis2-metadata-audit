import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategorycomboComponent } from './list/categorycombo.component';
import { CategorycomboDetailComponent } from './detail/categorycombo-detail.component';
import { CategorycomboRoutingModule } from './route/categorycombo-routing.module';
import { CategorycomboHistoryComponent } from './history/categorycombo-history.component';

@NgModule({
  imports: [SharedModule, CategorycomboRoutingModule],
  declarations: [CategorycomboComponent, CategorycomboDetailComponent, CategorycomboHistoryComponent],
})
export class CategorycomboModule {}
