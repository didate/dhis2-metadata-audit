import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptionsetDetailComponent } from './optionset-detail.component';

describe('OptionSet Management Detail Component', () => {
  let comp: OptionsetDetailComponent;
  let fixture: ComponentFixture<OptionsetDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OptionsetDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ optionset: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(OptionsetDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OptionsetDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load optionset on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.optionset).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
