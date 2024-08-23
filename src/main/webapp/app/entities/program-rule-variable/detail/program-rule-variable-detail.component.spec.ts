import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgramRuleVariableDetailComponent } from './program-rule-variable-detail.component';

describe('ProgramRuleVariable Management Detail Component', () => {
  let comp: ProgramRuleVariableDetailComponent;
  let fixture: ComponentFixture<ProgramRuleVariableDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgramRuleVariableDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ programRuleVariable: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProgramRuleVariableDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgramRuleVariableDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programRuleVariable on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.programRuleVariable).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
