import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataelementHistoryComponent } from './dataelement-history.component';

describe('DataelementHistoryComponent', () => {
  let component: DataelementHistoryComponent;
  let fixture: ComponentFixture<DataelementHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DataelementHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DataelementHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
