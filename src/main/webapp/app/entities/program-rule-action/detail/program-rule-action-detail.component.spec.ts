import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgramRuleActionDetailComponent } from './program-rule-action-detail.component';

describe('ProgramRuleAction Management Detail Component', () => {
  let comp: ProgramRuleActionDetailComponent;
  let fixture: ComponentFixture<ProgramRuleActionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgramRuleActionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ programRuleAction: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProgramRuleActionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgramRuleActionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programRuleAction on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.programRuleAction).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
