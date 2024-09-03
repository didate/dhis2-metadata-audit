import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganisationUnitHistoryComponent } from './organisation-unit-history.component';

describe('OrganisationUnitHistoryComponent', () => {
  let component: OrganisationUnitHistoryComponent;
  let fixture: ComponentFixture<OrganisationUnitHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrganisationUnitHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OrganisationUnitHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
