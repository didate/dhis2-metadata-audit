import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramIndicatorHistoryComponent } from './program-indicator-history.component';

describe('ProgramIndicatorHistoryComponent', () => {
  let component: ProgramIndicatorHistoryComponent;
  let fixture: ComponentFixture<ProgramIndicatorHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProgramIndicatorHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProgramIndicatorHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
