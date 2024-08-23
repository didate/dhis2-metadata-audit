import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';
import { IProgramStage } from '../program-stage.model';
import { ProgramStageService } from '../service/program-stage.service';
import { ProgramStageFormService } from './program-stage-form.service';

import { ProgramStageUpdateComponent } from './program-stage-update.component';

describe('ProgramStage Management Update Component', () => {
  let comp: ProgramStageUpdateComponent;
  let fixture: ComponentFixture<ProgramStageUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programStageFormService: ProgramStageFormService;
  let programStageService: ProgramStageService;
  let dHISUserService: DHISUserService;
  let programService: ProgramService;
  let dataelementService: DataelementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProgramStageUpdateComponent],
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
      .overrideTemplate(ProgramStageUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramStageUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programStageFormService = TestBed.inject(ProgramStageFormService);
    programStageService = TestBed.inject(ProgramStageService);
    dHISUserService = TestBed.inject(DHISUserService);
    programService = TestBed.inject(ProgramService);
    dataelementService = TestBed.inject(DataelementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DHISUser query and add missing value', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'b0fc28f5-1686-4822-b5bd-1882f7797ce9' };
      programStage.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'b43d6315-e779-46db-a19e-e2f4934b58d0' };
      programStage.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'd5b3119e-1c39-48a4-be43-bd091e7faa20' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const program: IProgram = { id: '2e094b80-b378-4876-8c44-dc4617a38682' };
      programStage.program = program;
      const programs: IProgram[] = [{ id: '5bc348db-babc-4c72-afb4-ea48bce67259' }];
      programStage.programs = programs;

      const programCollection: IProgram[] = [{ id: '50bf0cd0-5daf-4365-97ef-5b61d976bb1d' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program, ...programs];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataelement query and add missing value', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const programStageDataElements: IDataelement[] = [{ id: 'a82fdef9-10b9-4778-b71b-ca26482b0f95' }];
      programStage.programStageDataElements = programStageDataElements;

      const dataelementCollection: IDataelement[] = [{ id: 'a51075dd-ac91-4ab0-8efa-0338860de3e5' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [...programStageDataElements];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining),
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '569142c6-c440-4d19-844b-8b6b3ca5d59a' };
      programStage.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '2bdd3835-59e8-4b5e-a363-0e5c01f3fcf7' };
      programStage.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '9e0fc6d6-eb7d-4722-8d74-0b86be045a79' };
      programStage.program = program;
      const program: IProgram = { id: '66f60d55-2437-4d03-be08-2c2cac392266' };
      programStage.programs = [program];
      const programStageDataElements: IDataelement = { id: '067a4271-2691-421e-83e3-e99cbb57e469' };
      programStage.programStageDataElements = [programStageDataElements];

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.dataelementsSharedCollection).toContain(programStageDataElements);
      expect(comp.programStage).toEqual(programStage);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramStage>>();
      const programStage = { id: 'ABC' };
      jest.spyOn(programStageFormService, 'getProgramStage').mockReturnValue(programStage);
      jest.spyOn(programStageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programStage }));
      saveSubject.complete();

      // THEN
      expect(programStageFormService.getProgramStage).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programStageService.update).toHaveBeenCalledWith(expect.objectContaining(programStage));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramStage>>();
      const programStage = { id: 'ABC' };
      jest.spyOn(programStageFormService, 'getProgramStage').mockReturnValue({ id: null });
      jest.spyOn(programStageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programStage: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programStage }));
      saveSubject.complete();

      // THEN
      expect(programStageFormService.getProgramStage).toHaveBeenCalled();
      expect(programStageService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramStage>>();
      const programStage = { id: 'ABC' };
      jest.spyOn(programStageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programStageService.update).toHaveBeenCalled();
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

    describe('compareDataelement', () => {
      it('Should forward to dataelementService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(dataelementService, 'compareDataelement');
        comp.compareDataelement(entity, entity2);
        expect(dataelementService.compareDataelement).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
