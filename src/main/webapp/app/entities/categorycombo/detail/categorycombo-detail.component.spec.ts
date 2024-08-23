import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorycomboDetailComponent } from './categorycombo-detail.component';

describe('Categorycombo Management Detail Component', () => {
  let comp: CategorycomboDetailComponent;
  let fixture: ComponentFixture<CategorycomboDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategorycomboDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categorycombo: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(CategorycomboDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategorycomboDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categorycombo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categorycombo).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
