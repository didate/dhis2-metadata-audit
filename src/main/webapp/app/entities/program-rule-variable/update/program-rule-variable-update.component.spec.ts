import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

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
import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';
import { ProgramRuleVariableFormService } from './program-rule-variable-form.service';

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
      imports: [ProgramRuleVariableUpdateComponent],
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
      const project: IProject = { id: 2875 };
      programRuleVariable.project = project;

      const projectCollection: IProject[] = [{ id: 21551 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'b940de5e-29c0-409d-aace-cc9b9017e756' };
      programRuleVariable.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '2cf65868-226a-4e27-83c4-d5c263962b22' };
      programRuleVariable.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'fa514a9f-2adc-4574-a2e2-5dc08d71e139' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const program: IProgram = { id: '6d7e11a1-bc1d-4d73-8a99-7fbd2fb31180' };
      programRuleVariable.program = program;

      const programCollection: IProgram[] = [{ id: '000860d6-e681-450f-9f6c-b4b9531ab784' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrackedEntityAttribute query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '8bbba7c6-7403-488f-98ef-3f68f2b81cc4' };
      programRuleVariable.trackedEntityAttribute = trackedEntityAttribute;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: '7a666589-627b-4fe3-8c55-5253e49838a8' }];
      jest.spyOn(trackedEntityAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: trackedEntityAttributeCollection })));
      const additionalTrackedEntityAttributes = [trackedEntityAttribute];
      const expectedCollection: ITrackedEntityAttribute[] = [...additionalTrackedEntityAttributes, ...trackedEntityAttributeCollection];
      jest.spyOn(trackedEntityAttributeService, 'addTrackedEntityAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(trackedEntityAttributeService.query).toHaveBeenCalled();
      expect(trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        trackedEntityAttributeCollection,
        ...additionalTrackedEntityAttributes.map(expect.objectContaining),
      );
      expect(comp.trackedEntityAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataelement query and add missing value', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const dataElement: IDataelement = { id: 'c9b1aab0-fe4c-480b-8e7d-8958fe342234' };
      programRuleVariable.dataElement = dataElement;

      const dataelementCollection: IDataelement[] = [{ id: 'bd0c5c67-5ac2-46c5-9d0f-3315d52e555e' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [dataElement];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleVariable });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining),
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programRuleVariable: IProgramRuleVariable = { id: 'CBA' };
      const project: IProject = { id: 27247 };
      programRuleVariable.project = project;
      const createdBy: IDHISUser = { id: 'dab88731-da1d-43f1-8244-d01157d51377' };
      programRuleVariable.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '751a3ac9-1aba-4507-a697-b2aea98530d8' };
      programRuleVariable.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '9be5008c-cc77-4905-be86-2c98fbc9de33' };
      programRuleVariable.program = program;
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '877afaca-cf6e-47c2-8c5e-7430bec98263' };
      programRuleVariable.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: IDataelement = { id: '2dff6519-531c-4512-931f-345f46849675' };
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
