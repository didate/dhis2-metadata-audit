import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatasetHistoryComponent } from './dataset-history.component';

describe('DatasetHistoryComponent', () => {
  let component: DatasetHistoryComponent;
  let fixture: ComponentFixture<DatasetHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DatasetHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DatasetHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
