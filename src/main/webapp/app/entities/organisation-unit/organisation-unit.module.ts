import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrganisationUnitComponent } from './list/organisation-unit.component';
import { OrganisationUnitDetailComponent } from './detail/organisation-unit-detail.component';
import { OrganisationUnitRoutingModule } from './route/organisation-unit-routing.module';

@NgModule({
  imports: [SharedModule, OrganisationUnitRoutingModule],
  declarations: [OrganisationUnitComponent, OrganisationUnitDetailComponent],
})
export class OrganisationUnitModule {}
