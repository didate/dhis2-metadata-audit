import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IProgramIndicator } from '../program-indicator.model';
import { ProgramIndicatorService } from '../service/program-indicator.service';
import { ProgramIndicatorFormService } from './program-indicator-form.service';

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
      imports: [ProgramIndicatorUpdateComponent],
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
      const createdBy: IDHISUser = { id: '2b42d69b-dc3b-4a41-b739-e5c7f0f4de81' };
      programIndicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '901d6a2e-ee47-41fb-bc8e-0eb62d84063c' };
      programIndicator.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '2c5bdce7-2b6b-46a4-afda-a3ab85d52fab' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programIndicator: IProgramIndicator = { id: 'CBA' };
      const program: IProgram = { id: '3c80fc24-e1f5-47ba-b46a-bed0ce347d8a' };
      programIndicator.program = program;
      const programs: IProgram[] = [{ id: 'db512f96-486d-4901-9ff2-b25facb64028' }];
      programIndicator.programs = programs;

      const programCollection: IProgram[] = [{ id: '76c7d940-1d31-4e1e-b254-11cc4f178898' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program, ...programs];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programIndicator: IProgramIndicator = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '70ce2585-ba39-4956-a0c6-e9cc316cf9b2' };
      programIndicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '8c1ec015-8daa-4dd5-876c-c0b9e538489c' };
      programIndicator.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '067c1c9f-86a2-4a4d-8631-27735b2ce0b7' };
      programIndicator.program = program;
      const program: IProgram = { id: '09a86494-0768-4b18-9047-6a1ac49fc6e0' };
      programIndicator.programs = [program];

      activatedRoute.data = of({ programIndicator });
      comp.ngOnInit();

      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programsSharedCollection).toContain(program);
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
