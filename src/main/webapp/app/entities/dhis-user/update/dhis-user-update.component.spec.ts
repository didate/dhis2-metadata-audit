import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { DHISUserService } from '../service/dhis-user.service';
import { IDHISUser } from '../dhis-user.model';
import { DHISUserFormService } from './dhis-user-form.service';

import { DHISUserUpdateComponent } from './dhis-user-update.component';

describe('DHISUser Management Update Component', () => {
  let comp: DHISUserUpdateComponent;
  let fixture: ComponentFixture<DHISUserUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dHISUserFormService: DHISUserFormService;
  let dHISUserService: DHISUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DHISUserUpdateComponent],
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
      .overrideTemplate(DHISUserUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DHISUserUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dHISUserFormService = TestBed.inject(DHISUserFormService);
    dHISUserService = TestBed.inject(DHISUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dHISUser: IDHISUser = { id: 'CBA' };

      activatedRoute.data = of({ dHISUser });
      comp.ngOnInit();

      expect(comp.dHISUser).toEqual(dHISUser);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDHISUser>>();
      const dHISUser = { id: 'ABC' };
      jest.spyOn(dHISUserFormService, 'getDHISUser').mockReturnValue(dHISUser);
      jest.spyOn(dHISUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dHISUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dHISUser }));
      saveSubject.complete();

      // THEN
      expect(dHISUserFormService.getDHISUser).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dHISUserService.update).toHaveBeenCalledWith(expect.objectContaining(dHISUser));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDHISUser>>();
      const dHISUser = { id: 'ABC' };
      jest.spyOn(dHISUserFormService, 'getDHISUser').mockReturnValue({ id: null });
      jest.spyOn(dHISUserService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dHISUser: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dHISUser }));
      saveSubject.complete();

      // THEN
      expect(dHISUserFormService.getDHISUser).toHaveBeenCalled();
      expect(dHISUserService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDHISUser>>();
      const dHISUser = { id: 'ABC' };
      jest.spyOn(dHISUserService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dHISUser });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dHISUserService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
