import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DHISUserDetailComponent } from './dhis-user-detail.component';

describe('DHISUser Management Detail Component', () => {
  let comp: DHISUserDetailComponent;
  let fixture: ComponentFixture<DHISUserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DHISUserDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dHISUser: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(DHISUserDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DHISUserDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dHISUser on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dHISUser).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
