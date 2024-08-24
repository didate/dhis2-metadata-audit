import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OptionsetFormService } from './optionset-form.service';
import { OptionsetService } from '../service/optionset.service';
import { IOptionset } from '../optionset.model';

import { OptionsetUpdateComponent } from './optionset-update.component';

describe('OptionSet Management Update Component', () => {
  let comp: OptionsetUpdateComponent;
  let fixture: ComponentFixture<OptionsetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let optionsetFormService: OptionsetFormService;
  let optionsetService: OptionsetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OptionsetUpdateComponent],
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
      .overrideTemplate(OptionsetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OptionsetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    optionsetFormService = TestBed.inject(OptionsetFormService);
    optionsetService = TestBed.inject(OptionsetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const optionset: IOptionset = { id: 'CBA' };

      activatedRoute.data = of({ optionset });
      comp.ngOnInit();

      expect(comp.optionset).toEqual(optionset);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOptionset>>();
      const optionset = { id: 'ABC' };
      jest.spyOn(optionsetFormService, 'getOptionset').mockReturnValue(optionset);
      jest.spyOn(optionsetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ optionset });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: optionset }));
      saveSubject.complete();

      // THEN
      expect(optionsetFormService.getOptionset).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(optionsetService.update).toHaveBeenCalledWith(expect.objectContaining(optionset));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOptionset>>();
      const optionset = { id: 'ABC' };
      jest.spyOn(optionsetFormService, 'getOptionset').mockReturnValue({ id: null });
      jest.spyOn(optionsetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ optionset: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: optionset }));
      saveSubject.complete();

      // THEN
      expect(optionsetFormService.getOptionset).toHaveBeenCalled();
      expect(optionsetService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOptionset>>();
      const optionset = { id: 'ABC' };
      jest.spyOn(optionsetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ optionset });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(optionsetService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
