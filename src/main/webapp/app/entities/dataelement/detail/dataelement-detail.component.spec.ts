import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataelementDetailComponent } from './dataelement-detail.component';

describe('Dataelement Management Detail Component', () => {
  let comp: DataelementDetailComponent;
  let fixture: ComponentFixture<DataelementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DataelementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dataelement: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(DataelementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DataelementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dataelement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dataelement).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
