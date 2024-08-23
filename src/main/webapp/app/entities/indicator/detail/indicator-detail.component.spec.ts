import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IndicatorDetailComponent } from './indicator-detail.component';

describe('Indicator Management Detail Component', () => {
  let comp: IndicatorDetailComponent;
  let fixture: ComponentFixture<IndicatorDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndicatorDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ indicator: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(IndicatorDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IndicatorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load indicator on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.indicator).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
