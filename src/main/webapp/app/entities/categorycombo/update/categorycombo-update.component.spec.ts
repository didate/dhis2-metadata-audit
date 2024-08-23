import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategorycomboFormService } from './categorycombo-form.service';
import { CategorycomboService } from '../service/categorycombo.service';
import { ICategorycombo } from '../categorycombo.model';

import { CategorycomboUpdateComponent } from './categorycombo-update.component';

describe('Categorycombo Management Update Component', () => {
  let comp: CategorycomboUpdateComponent;
  let fixture: ComponentFixture<CategorycomboUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categorycomboFormService: CategorycomboFormService;
  let categorycomboService: CategorycomboService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategorycomboUpdateComponent],
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
      .overrideTemplate(CategorycomboUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategorycomboUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categorycomboFormService = TestBed.inject(CategorycomboFormService);
    categorycomboService = TestBed.inject(CategorycomboService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categorycombo: ICategorycombo = { id: 'CBA' };

      activatedRoute.data = of({ categorycombo });
      comp.ngOnInit();

      expect(comp.categorycombo).toEqual(categorycombo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorycombo>>();
      const categorycombo = { id: 'ABC' };
      jest.spyOn(categorycomboFormService, 'getCategorycombo').mockReturnValue(categorycombo);
      jest.spyOn(categorycomboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorycombo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorycombo }));
      saveSubject.complete();

      // THEN
      expect(categorycomboFormService.getCategorycombo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categorycomboService.update).toHaveBeenCalledWith(expect.objectContaining(categorycombo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorycombo>>();
      const categorycombo = { id: 'ABC' };
      jest.spyOn(categorycomboFormService, 'getCategorycombo').mockReturnValue({ id: null });
      jest.spyOn(categorycomboService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorycombo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categorycombo }));
      saveSubject.complete();

      // THEN
      expect(categorycomboFormService.getCategorycombo).toHaveBeenCalled();
      expect(categorycomboService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategorycombo>>();
      const categorycombo = { id: 'ABC' };
      jest.spyOn(categorycomboService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categorycombo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categorycomboService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
