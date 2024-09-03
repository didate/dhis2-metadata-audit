import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramRuleHistoryComponent } from './program-rule-history.component';

describe('ProgramRuleHistoryComponent', () => {
  let component: ProgramRuleHistoryComponent;
  let fixture: ComponentFixture<ProgramRuleHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProgramRuleHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProgramRuleHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
