import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndicatortypeHistoryComponent } from './indicatortype-history.component';

describe('IndicatortypeHistoryComponent', () => {
  let component: IndicatortypeHistoryComponent;
  let fixture: ComponentFixture<IndicatortypeHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [IndicatortypeHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(IndicatortypeHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
