import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramIndicatorFormService } from './program-indicator-form.service';
import { ProgramIndicatorService } from '../service/program-indicator.service';
import { IProgramIndicator } from '../program-indicator.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';

import { ProgramIndicatorUpdateComponent } from './program-indicator-update.component';

describe('ProgramIndicator Management Update Component', () => {
  let comp: ProgramIndicatorUpdateComponent;
  let fixture: ComponentFixture<ProgramIndicatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programIndicatorFormService: ProgramIndicatorFormService;
  let programIndicatorService: ProgramIndicatorService;
  let dHISUserService: DHISUserService;
  let programService: ProgramService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramIndicatorUpdateComponent],
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
      .overrideTemplate(ProgramIndicatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramIndicatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programIndicatorFormService = TestBed.inject(ProgramIndicatorFormService);
    programIndicatorService = TestBed.inject(ProgramIndicatorService);
    dHISUserService = TestBed.inject(DHISUserService);
    programService = TestBed.inject(ProgramService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DHISUser query and add missing value', () => {
      const programIndicator: IProgramIndicator = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '79e7c6a1-e038-4b95-8c7a-c9e436f39506' };
      programIndicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '1e2ba7a0-c7f3-4edb-8f07-4ba47dfd54b0' };
      programIndicator.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '3e2f5cd3-1b99-4cbc-a301-26dd4d3bebad' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programIndicator: IProgramIndicator = { id: 'CBA' };
      const program: IProgram = { id: '8997e7fc-7239-49df-af72-694a24051bb9' };
      programIndicator.program = program;

      const programCollection: IProgram[] = [{ id: 'af49227f-5597-49db-9ef5-a014d742b5b4' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining)
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programIndicator: IProgramIndicator = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '9a721e07-6011-453b-b9a7-c61863692a94' };
      programIndicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '1659fdce-195a-45cb-b97b-ff91bf925b3f' };
      programIndicator.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: 'f1d17e82-559d-458e-8cd8-4a12082131a6' };
      programIndicator.program = program;

      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.programIndicator).toEqual(programIndicator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramIndicator>>();
      const programIndicator = { id: 'ABC' };
      jest.spyOn(programIndicatorFormService, 'getProgramIndicator').mockReturnValue(programIndicator);
      jest.spyOn(programIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programIndicator }));
      saveSubject.complete();

      // THEN
      expect(programIndicatorFormService.getProgramIndicator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programIndicatorService.update).toHaveBeenCalledWith(expect.objectContaining(programIndicator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramIndicator>>();
      const programIndicator = { id: 'ABC' };
      jest.spyOn(programIndicatorFormService, 'getProgramIndicator').mockReturnValue({ id: null });
      jest.spyOn(programIndicatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programIndicator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programIndicator }));
      saveSubject.complete();

      // THEN
      expect(programIndicatorFormService.getProgramIndicator).toHaveBeenCalled();
      expect(programIndicatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramIndicator>>();
      const programIndicator = { id: 'ABC' };
      jest.spyOn(programIndicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programIndicatorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDHISUser', () => {
      it('Should forward to dHISUserService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(dHISUserService, 'compareDHISUser');
        comp.compareDHISUser(entity, entity2);
        expect(dHISUserService.compareDHISUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProgram', () => {
      it('Should forward to programService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programService, 'compareProgram');
        comp.compareProgram(entity, entity2);
        expect(programService.compareProgram).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
