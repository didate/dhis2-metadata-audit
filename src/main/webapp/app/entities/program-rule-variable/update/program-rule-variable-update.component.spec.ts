import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramRuleVariableFormService } from './program-rule-variable-form.service';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';
import { IProgramRuleVariable } from '../program-rule-variable.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from 'app/entities/tracked-entity-attribute/service/tracked-entity-attribute.service';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';

import { ProgramRuleVariableUpdateComponent } from './program-rule-variable-update.component';

describe('ProgramRuleVariable Management Update Component', () => {
  let comp: ProgramRuleVariableUpdateComponent;
  let fixture: ComponentFixture<ProgramRuleVariableUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programRuleVariableFormService: ProgramRuleVariableFormService;
  let programRuleVariableService: ProgramRuleVariableService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let programService: ProgramService;
  let trackedEntityAttributeService: TrackedEntityAttributeService;
  let dataelementService: DataelementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramRuleVariableUpdateComponent],
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
      .overrideTemplate(ProgramRuleVariableUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramRuleVariableUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programRuleVariableFormService = TestBed.inject(ProgramRuleVariableFormService);
    programRuleVariableService = TestBed.inject(ProgramRuleVariableService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    programService = TestBed.inject(ProgramService);
    trackedEntityAttributeService = TestBed.inject(TrackedEntityAttributeService);
    dataelementService = TestBed.inject(DataelementService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const project: IProject = { id: 33698 };
      programRuleVariable.project = project;

      const projectCollection: IProject[] = [{ id: 31946 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '876d9cef-10fe-4b22-9180-e598f5f0f9c4' };
      programRuleVariable.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'fcb2afa0-1d32-44e0-ab33-7e240af651a3' };
      programRuleVariable.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '223a0e92-32a8-4544-9813-477f8ebc1ca8' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const program: IProgram = { id: 'ded9d912-43fe-4aa1-9476-f82f825b9879' };
      programRuleVariable.program = program;

      const programCollection: IProgram[] = [{ id: 'b39e8922-d454-459f-8555-b4dbd4840f24' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining)
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrackedEntityAttribute query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'a9b4d83c-4c2d-452e-aad1-7ed441980e50' };
      programRuleVariable.trackedEntityAttribute = trackedEntityAttribute;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: '2cb640ba-362d-49a6-aac1-9578add2274c' }];
      jest.spyOn(trackedEntityAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: trackedEntityAttributeCollection })));
      const additionalTrackedEntityAttributes = [trackedEntityAttribute];
      const expectedCollection: ITrackedEntityAttribute[] = [...additionalTrackedEntityAttributes, ...trackedEntityAttributeCollection];
      jest.spyOn(trackedEntityAttributeService, 'addTrackedEntityAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(trackedEntityAttributeService.query).toHaveBeenCalled();
      expect(trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        trackedEntityAttributeCollection,
        ...additionalTrackedEntityAttributes.map(expect.objectContaining)
      );
      expect(comp.trackedEntityAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataelement query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const dataElement: IDataelement = { id: '0aaea7bc-0287-4099-9c39-e6d3051f1e3c' };
      programRuleVariable.dataElement = dataElement;

      const dataelementCollection: IDataelement[] = [{ id: '934db644-0567-4cae-a700-7304cd253981' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [dataElement];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining)
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const project: IProject = { id: 79473 };
      programRuleVariable.project = project;
      const createdBy: IDHISUser = { id: '27517d05-014e-4f14-a2c5-46be4c4ea738' };
      programRuleVariable.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '1815574a-f720-4b57-8277-78aa868ac1b7' };
      programRuleVariable.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '91c8b0d4-966c-4c8e-8e7d-7f9e6a506acb' };
      programRuleVariable.program = program;
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'feec7be1-9044-4a2e-8733-e02829f07cd2' };
      programRuleVariable.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: IDataelement = { id: '41e027f0-2acb-49f3-a10e-683309a973d4' };
      programRuleVariable.dataElement = dataElement;

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.trackedEntityAttributesSharedCollection).toContain(trackedEntityAttribute);
      expect(comp.dataelementsSharedCollection).toContain(dataElement);
      expect(comp.programRuleVariable).toEqual(programRuleVariable);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRuleVariable>>();
      const programRuleVariable = { id: 'ABC' };
      jest.spyOn(programRuleVariableFormService, 'getProgramRuleVariable').mockReturnValue(programRuleVariable);
      jest.spyOn(programRuleVariableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programRuleVariable }));
      saveSubject.complete();

      // THEN
      expect(programRuleVariableFormService.getProgramRuleVariable).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programRuleVariableService.update).toHaveBeenCalledWith(expect.objectContaining(programRuleVariable));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRuleVariable>>();
      const programRuleVariable = { id: 'ABC' };
      jest.spyOn(programRuleVariableFormService, 'getProgramRuleVariable').mockReturnValue({ id: null });
      jest.spyOn(programRuleVariableService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRuleVariable: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programRuleVariable }));
      saveSubject.complete();

      // THEN
      expect(programRuleVariableFormService.getProgramRuleVariable).toHaveBeenCalled();
      expect(programRuleVariableService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRuleVariable>>();
      const programRuleVariable = { id: 'ABC' };
      jest.spyOn(programRuleVariableService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programRuleVariableService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProject', () => {
      it('Should forward to projectService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(projectService, 'compareProject');
        comp.compareProject(entity, entity2);
        expect(projectService.compareProject).toHaveBeenCalledWith(entity, entity2);
      });
    });

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

    describe('compareTrackedEntityAttribute', () => {
      it('Should forward to trackedEntityAttributeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(trackedEntityAttributeService, 'compareTrackedEntityAttribute');
        comp.compareTrackedEntityAttribute(entity, entity2);
        expect(trackedEntityAttributeService.compareTrackedEntityAttribute).toHaveBeenCalledWith(entity, entity2);
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
