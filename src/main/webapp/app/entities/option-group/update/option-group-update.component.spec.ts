import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OptionGroupFormService } from './option-group-form.service';
import { OptionGroupService } from '../service/option-group.service';
import { IOptionGroup } from '../option-group.model';

import { OptionGroupUpdateComponent } from './option-group-update.component';

describe('OptionGroup Management Update Component', () => {
  let comp: OptionGroupUpdateComponent;
  let fixture: ComponentFixture<OptionGroupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let optionGroupFormService: OptionGroupFormService;
  let optionGroupService: OptionGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OptionGroupUpdateComponent],
      providers: [
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
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOptionGroup>>();
      const optionGroup = { id: 'ABC' };
      jest.spyOn(optionGroupFormService, 'getOptionGroup').mockReturnValue(optionGroup);
      jest.spyOn(optionGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ optionGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: optionGroup }));
      saveSubject.complete();

      // THEN
      expect(optionGroupFormService.getOptionGroup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(optionGroupService.update).toHaveBeenCalledWith(expect.objectContaining(optionGroup));
      expect(comp.isSaving).toEqual(false);
    });

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

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOptionGroup>>();
      const optionGroup = { id: 'ABC' };
      jest.spyOn(optionGroupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ optionGroup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(optionGroupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
