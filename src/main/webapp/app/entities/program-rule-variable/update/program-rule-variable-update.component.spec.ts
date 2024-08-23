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
      const project: IProject = { id: 17170 };
      programRuleVariable.project = project;

      const projectCollection: IProject[] = [{ id: 25831 }];
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
      const createdBy: IDHISUser = { id: '7c1d78d8-a5fc-4722-a915-7c85c45c7a63' };
      programRuleVariable.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'c265f5e8-8976-4a73-b2cd-a47e6b684301' };
      programRuleVariable.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '3a486e5d-92ef-4b6d-a551-f71f17d2119b' }];
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
      const program: IProgram = { id: 'd9b881ce-9d36-4959-bad9-fe5d71a24a47' };
      programRuleVariable.program = program;

      const programCollection: IProgram[] = [{ id: '91ca0439-5b70-4624-a86c-75e4dfc84858' }];
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
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '722e7e17-e356-4626-9a55-cd4fcb147186' };
      programRuleVariable.trackedEntityAttribute = trackedEntityAttribute;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: 'e08cfa8b-9334-4626-9741-beacd434583c' }];
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
      const dataElement: IDataelement = { id: '4c279eb3-8ca1-45ef-ae01-abbb081db524' };
      programRuleVariable.dataElement = dataElement;

      const dataelementCollection: IDataelement[] = [{ id: '8596feb3-2ab2-4750-831a-15184e4ad738' }];
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
      const project: IProject = { id: 1285 };
      programRuleVariable.project = project;
      const createdBy: IDHISUser = { id: '437effae-c8bb-4500-88fd-c2cf4c0ef60d' };
      programRuleVariable.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '49da631e-3197-4bfd-9133-ddc69352cd0e' };
      programRuleVariable.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '0a0b3c4f-afc5-4494-984f-4bdac481b149' };
      programRuleVariable.program = program;
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '95f9d68d-354f-46fa-9b9e-de1d917449d7' };
      programRuleVariable.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: IDataelement = { id: '037dc16d-d33e-4f40-9d38-0f19a4f422e5' };
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
