import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgramRuleVariableHistoryComponent } from './program-rule-variable-history.component';

describe('ProgramRuleVariableHistoryComponent', () => {
  let component: ProgramRuleVariableHistoryComponent;
  let fixture: ComponentFixture<ProgramRuleVariableHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProgramRuleVariableHistoryComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ProgramRuleVariableHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
