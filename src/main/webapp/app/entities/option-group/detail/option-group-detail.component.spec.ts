import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptionGroupDetailComponent } from './option-group-detail.component';

describe('OptionGroup Management Detail Component', () => {
  let comp: OptionGroupDetailComponent;
  let fixture: ComponentFixture<OptionGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OptionGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ optionGroup: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(OptionGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OptionGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load optionGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.optionGroup).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
