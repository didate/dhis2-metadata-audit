import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonNotifierDetailComponent } from './person-notifier-detail.component';

describe('PersonNotifier Management Detail Component', () => {
  let comp: PersonNotifierDetailComponent;
  let fixture: ComponentFixture<PersonNotifierDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PersonNotifierDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ personNotifier: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PersonNotifierDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PersonNotifierDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load personNotifier on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.personNotifier).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
