import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DatasetDetailComponent } from './dataset-detail.component';

describe('Dataset Management Detail Component', () => {
  let comp: DatasetDetailComponent;
  let fixture: ComponentFixture<DatasetDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DatasetDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dataset: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(DatasetDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DatasetDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dataset on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dataset).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
