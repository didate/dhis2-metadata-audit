import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { OptionGroupService } from '../service/option-group.service';
import { IOptionGroup } from '../option-group.model';
import { OptionGroupFormService } from './option-group-form.service';

import { OptionGroupUpdateComponent } from './option-group-update.component';

describe('OptionGroup Management Update Component', () => {
  let comp: OptionGroupUpdateComponent;
  let fixture: ComponentFixture<OptionGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let optionGroupFormService: OptionGroupFormService;
  let optionGroupService: OptionGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OptionGroupUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(OptionGroupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OptionGroupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    optionGroupFormService = TestBed.inject(OptionGroupFormService);
    optionGroupService = TestBed.inject(OptionGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const optionGroup: IOptionGroup = { id: 'CBA' };

      activatedRoute.data = of({ optionGroup });
      comp.ngOnInit();

      expect(comp.optionGroup).toEqual(optionGroup);
    });
  });

  describe('save', () => {
    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOptionGroup>>();
      const optionGroup = { id: 'ABC' };
      jest.spyOn(optionGroupFormService, 'getOptionGroup').mockReturnValue({ id: null });
      jest.spyOn(optionGroupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ optionGroup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: optionGroup }));
      saveSubject.complete();

      // THEN
      expect(optionGroupFormService.getOptionGroup).toHaveBeenCalled();
      expect(optionGroupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });
  });
});
