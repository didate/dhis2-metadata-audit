import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramRuleActionFormService } from './program-rule-action-form.service';
import { ProgramRuleActionService } from '../service/program-rule-action.service';
import { IProgramRuleAction } from '../program-rule-action.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgramRule } from 'app/entities/program-rule/program-rule.model';
import { ProgramRuleService } from 'app/entities/program-rule/service/program-rule.service';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from 'app/entities/tracked-entity-attribute/service/tracked-entity-attribute.service';
import { IOptionGroup } from 'app/entities/option-group/option-group.model';
import { OptionGroupService } from 'app/entities/option-group/service/option-group.service';

import { ProgramRuleActionUpdateComponent } from './program-rule-action-update.component';

describe('ProgramRuleAction Management Update Component', () => {
  let comp: ProgramRuleActionUpdateComponent;
  let fixture: ComponentFixture<ProgramRuleActionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programRuleActionFormService: ProgramRuleActionFormService;
  let programRuleActionService: ProgramRuleActionService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let programRuleService: ProgramRuleService;
  let trackedEntityAttributeService: TrackedEntityAttributeService;
  let optionGroupService: OptionGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramRuleActionUpdateComponent],
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
      .overrideTemplate(ProgramRuleActionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramRuleActionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programRuleActionFormService = TestBed.inject(ProgramRuleActionFormService);
    programRuleActionService = TestBed.inject(ProgramRuleActionService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    programRuleService = TestBed.inject(ProgramRuleService);
    trackedEntityAttributeService = TestBed.inject(TrackedEntityAttributeService);
    optionGroupService = TestBed.inject(OptionGroupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const project: IProject = { id: 60679 };
      programRuleAction.project = project;

      const projectCollection: IProject[] = [{ id: 3195 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '020b7e8d-ae3b-4217-9a15-00569709e2ae' };
      programRuleAction.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '7b654f70-a4f8-4ef8-8c38-0c3a78fdac4a' };
      programRuleAction.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '5c6d7d7d-7b75-40a1-b7ea-edc4502edc0b' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProgramRule query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const programRule: IProgramRule = { id: 'b44ae9ca-6929-4d61-971f-6acb18451472' };
      programRuleAction.programRule = programRule;

      const programRuleCollection: IProgramRule[] = [{ id: '1e43f1b8-ef0e-4a31-8bbf-5e6a9134ef2d' }];
      jest.spyOn(programRuleService, 'query').mockReturnValue(of(new HttpResponse({ body: programRuleCollection })));
      const additionalProgramRules = [programRule];
      const expectedCollection: IProgramRule[] = [...additionalProgramRules, ...programRuleCollection];
      jest.spyOn(programRuleService, 'addProgramRuleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(programRuleService.query).toHaveBeenCalled();
      expect(programRuleService.addProgramRuleToCollectionIfMissing).toHaveBeenCalledWith(
        programRuleCollection,
        ...additionalProgramRules.map(expect.objectContaining)
      );
      expect(comp.programRulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrackedEntityAttribute query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'ed290cc4-c225-4cc3-b270-68a2436f8102' };
      programRuleAction.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: ITrackedEntityAttribute = { id: 'd13e33d7-6eaa-49fe-9721-970e8a077b1d' };
      programRuleAction.dataElement = dataElement;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: '91649d86-c0c4-48f5-92b4-f6dcd96e293d' }];
      jest.spyOn(trackedEntityAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: trackedEntityAttributeCollection })));
      const additionalTrackedEntityAttributes = [trackedEntityAttribute, dataElement];
      const expectedCollection: ITrackedEntityAttribute[] = [...additionalTrackedEntityAttributes, ...trackedEntityAttributeCollection];
      jest.spyOn(trackedEntityAttributeService, 'addTrackedEntityAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(trackedEntityAttributeService.query).toHaveBeenCalled();
      expect(trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        trackedEntityAttributeCollection,
        ...additionalTrackedEntityAttributes.map(expect.objectContaining)
      );
      expect(comp.trackedEntityAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OptionGroup query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const optionGroup: IOptionGroup = { id: '32b6c167-2f66-4450-9edc-ed2b7e05b894' };
      programRuleAction.optionGroup = optionGroup;

      const optionGroupCollection: IOptionGroup[] = [{ id: 'a727a6f7-15ea-4b88-9ff0-f3305ab37355' }];
      jest.spyOn(optionGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: optionGroupCollection })));
      const additionalOptionGroups = [optionGroup];
      const expectedCollection: IOptionGroup[] = [...additionalOptionGroups, ...optionGroupCollection];
      jest.spyOn(optionGroupService, 'addOptionGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(optionGroupService.query).toHaveBeenCalled();
      expect(optionGroupService.addOptionGroupToCollectionIfMissing).toHaveBeenCalledWith(
        optionGroupCollection,
        ...additionalOptionGroups.map(expect.objectContaining)
      );
      expect(comp.optionGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const project: IProject = { id: 64519 };
      programRuleAction.project = project;
      const createdBy: IDHISUser = { id: '5824dee5-2ddf-44cd-bd4a-b36dcde91c62' };
      programRuleAction.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '6f3c1af7-5e9c-4d29-8f22-e531390e74ac' };
      programRuleAction.lastUpdatedBy = lastUpdatedBy;
      const programRule: IProgramRule = { id: '1de9c177-3189-4ef1-9efa-e24cba1a2701' };
      programRuleAction.programRule = programRule;
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '2b57d7ef-2ba9-4992-9b27-bc1f8c7c91fd' };
      programRuleAction.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: ITrackedEntityAttribute = { id: '2c04bf4c-0d99-46f9-b66a-6a9f1d758d2c' };
      programRuleAction.dataElement = dataElement;
      const optionGroup: IOptionGroup = { id: '921f6e14-a3bd-4d63-b2d3-b19ccc6e51d7' };
      programRuleAction.optionGroup = optionGroup;

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programRulesSharedCollection).toContain(programRule);
      expect(comp.trackedEntityAttributesSharedCollection).toContain(trackedEntityAttribute);
      expect(comp.trackedEntityAttributesSharedCollection).toContain(dataElement);
      expect(comp.optionGroupsSharedCollection).toContain(optionGroup);
      expect(comp.programRuleAction).toEqual(programRuleAction);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRuleAction>>();
      const programRuleAction = { id: 'ABC' };
      jest.spyOn(programRuleActionFormService, 'getProgramRuleAction').mockReturnValue(programRuleAction);
      jest.spyOn(programRuleActionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programRuleAction }));
      saveSubject.complete();

      // THEN
      expect(programRuleActionFormService.getProgramRuleAction).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programRuleActionService.update).toHaveBeenCalledWith(expect.objectContaining(programRuleAction));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRuleAction>>();
      const programRuleAction = { id: 'ABC' };
      jest.spyOn(programRuleActionFormService, 'getProgramRuleAction').mockReturnValue({ id: null });
      jest.spyOn(programRuleActionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRuleAction: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programRuleAction }));
      saveSubject.complete();

      // THEN
      expect(programRuleActionFormService.getProgramRuleAction).toHaveBeenCalled();
      expect(programRuleActionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRuleAction>>();
      const programRuleAction = { id: 'ABC' };
      jest.spyOn(programRuleActionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programRuleActionService.update).toHaveBeenCalled();
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

    describe('compareProgramRule', () => {
      it('Should forward to programRuleService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programRuleService, 'compareProgramRule');
        comp.compareProgramRule(entity, entity2);
        expect(programRuleService.compareProgramRule).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareOptionGroup', () => {
      it('Should forward to optionGroupService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(optionGroupService, 'compareOptionGroup');
        comp.compareOptionGroup(entity, entity2);
        expect(optionGroupService.compareOptionGroup).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
