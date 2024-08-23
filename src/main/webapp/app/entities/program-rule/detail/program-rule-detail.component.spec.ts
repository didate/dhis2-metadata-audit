import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgramRuleDetailComponent } from './program-rule-detail.component';

describe('ProgramRule Management Detail Component', () => {
  let comp: ProgramRuleDetailComponent;
  let fixture: ComponentFixture<ProgramRuleDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgramRuleDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ programRule: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProgramRuleDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgramRuleDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programRule on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.programRule).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
