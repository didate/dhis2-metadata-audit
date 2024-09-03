import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DhisUserHistoryComponent } from './dhis-user-history.component';

describe('DhisUserHistoryComponent', () => {
  let component: DhisUserHistoryComponent;
  let fixture: ComponentFixture<DhisUserHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DhisUserHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DhisUserHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
