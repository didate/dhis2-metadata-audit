import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IndicatortypeDetailComponent } from './indicatortype-detail.component';

describe('Indicatortype Management Detail Component', () => {
  let comp: IndicatortypeDetailComponent;
  let fixture: ComponentFixture<IndicatortypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IndicatortypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ indicatortype: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(IndicatortypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IndicatortypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load indicatortype on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.indicatortype).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
