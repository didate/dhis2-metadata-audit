import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramRuleActionHistoryComponent } from './program-rule-action-history.component';

describe('ProgramRuleActionHistoryComponent', () => {
  let component: ProgramRuleActionHistoryComponent;
  let fixture: ComponentFixture<ProgramRuleActionHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProgramRuleActionHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProgramRuleActionHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
