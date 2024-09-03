import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramStageHistoryComponent } from './program-stage-history.component';

describe('ProgramStageHistoryComponent', () => {
  let component: ProgramStageHistoryComponent;
  let fixture: ComponentFixture<ProgramStageHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProgramStageHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProgramStageHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
