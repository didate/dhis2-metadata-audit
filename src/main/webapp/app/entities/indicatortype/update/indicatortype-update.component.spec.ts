import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IndicatortypeFormService } from './indicatortype-form.service';
import { IndicatortypeService } from '../service/indicatortype.service';
import { IIndicatortype } from '../indicatortype.model';

import { IndicatortypeUpdateComponent } from './indicatortype-update.component';

describe('Indicatortype Management Update Component', () => {
  let comp: IndicatortypeUpdateComponent;
  let fixture: ComponentFixture<IndicatortypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let indicatortypeFormService: IndicatortypeFormService;
  let indicatortypeService: IndicatortypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IndicatortypeUpdateComponent],
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
      .overrideTemplate(IndicatortypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IndicatortypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    indicatortypeFormService = TestBed.inject(IndicatortypeFormService);
    indicatortypeService = TestBed.inject(IndicatortypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const indicatortype: IIndicatortype = { id: 'CBA' };

      activatedRoute.data = of({ indicatortype });
      comp.ngOnInit();

      expect(comp.indicatortype).toEqual(indicatortype);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicatortype>>();
      const indicatortype = { id: 'ABC' };
      jest.spyOn(indicatortypeFormService, 'getIndicatortype').mockReturnValue(indicatortype);
      jest.spyOn(indicatortypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicatortype });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicatortype }));
      saveSubject.complete();

      // THEN
      expect(indicatortypeFormService.getIndicatortype).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(indicatortypeService.update).toHaveBeenCalledWith(expect.objectContaining(indicatortype));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicatortype>>();
      const indicatortype = { id: 'ABC' };
      jest.spyOn(indicatortypeFormService, 'getIndicatortype').mockReturnValue({ id: null });
      jest.spyOn(indicatortypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicatortype: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicatortype }));
      saveSubject.complete();

      // THEN
      expect(indicatortypeFormService.getIndicatortype).toHaveBeenCalled();
      expect(indicatortypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicatortype>>();
      const indicatortype = { id: 'ABC' };
      jest.spyOn(indicatortypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicatortype });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(indicatortypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
