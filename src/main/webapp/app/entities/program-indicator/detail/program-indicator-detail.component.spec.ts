import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProgramIndicatorDetailComponent } from './program-indicator-detail.component';

describe('ProgramIndicator Management Detail Component', () => {
  let comp: ProgramIndicatorDetailComponent;
  let fixture: ComponentFixture<ProgramIndicatorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgramIndicatorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ programIndicator: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProgramIndicatorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProgramIndicatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load programIndicator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.programIndicator).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
