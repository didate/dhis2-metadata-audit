import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramStageFormService } from './program-stage-form.service';
import { ProgramStageService } from '../service/program-stage.service';
import { IProgramStage } from '../program-stage.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';

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
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramStageUpdateComponent],
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
      const createdBy: IDHISUser = { id: 'b1402f6c-2e81-4369-8fd5-dba2175c3813' };
      programStage.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'e535ac3c-61a1-491d-86c1-f2e8df3ce4b5' };
      programStage.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'ddd67f0f-95b8-4e87-ad61-a47e899454d9' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const program: IProgram = { id: '01c880fc-9568-4eb4-9351-0142b722f87c' };
      programStage.program = program;

      const programCollection: IProgram[] = [{ id: 'e63e4e0c-96d4-40b1-bd81-e0ff3ad9c9b6' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining)
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataelement query and add missing value', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const programStageDataElements: IDataelement[] = [{ id: 'a6576890-ffbb-411a-ba41-ba93db0af3e9' }];
      programStage.programStageDataElements = programStageDataElements;

      const dataelementCollection: IDataelement[] = [{ id: '8af2a735-ab66-4d85-9d22-dd714ff13f0c' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [...programStageDataElements];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining)
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programStage: IProgramStage = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '4d1f8b9a-aaf0-45ce-8a36-4dc5884786ce' };
      programStage.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '17e15e2a-c050-4806-ab34-2f41c67d90bb' };
      programStage.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: 'd9008b86-ef28-465f-b83c-09261e056e15' };
      programStage.program = program;
      const programStageDataElements: IDataelement = { id: 'd777ef85-4c7b-44e4-9a4d-614e23911ddc' };
      programStage.programStageDataElements = [programStageDataElements];

      activatedRoute.data = of({ programStage });
      comp.ngOnInit();

      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
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
