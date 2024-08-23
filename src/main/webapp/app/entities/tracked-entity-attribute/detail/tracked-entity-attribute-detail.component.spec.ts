import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrackedEntityAttributeDetailComponent } from './tracked-entity-attribute-detail.component';

describe('TrackedEntityAttribute Management Detail Component', () => {
  let comp: TrackedEntityAttributeDetailComponent;
  let fixture: ComponentFixture<TrackedEntityAttributeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrackedEntityAttributeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trackedEntityAttribute: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TrackedEntityAttributeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrackedEntityAttributeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trackedEntityAttribute on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trackedEntityAttribute).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
