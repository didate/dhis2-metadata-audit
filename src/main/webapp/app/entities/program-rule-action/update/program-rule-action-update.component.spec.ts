import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

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
import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';
import { ProgramRuleActionFormService } from './program-rule-action-form.service';

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
      imports: [ProgramRuleActionUpdateComponent],
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
      const project: IProject = { id: 16341 };
      programRuleAction.project = project;

      const projectCollection: IProject[] = [{ id: 14591 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'bbeb5cc1-88c5-4214-b373-fe37fe969b12' };
      programRuleAction.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '33586838-892e-449a-bf39-6650860554a5' };
      programRuleAction.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'a6cbf4ba-7fa4-44cf-bdd5-4e53ed0d6b2b' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProgramRule query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const programRule: IProgramRule = { id: 'b1ef3958-10d2-49fb-90a7-7739b33a8015' };
      programRuleAction.programRule = programRule;

      const programRuleCollection: IProgramRule[] = [{ id: '10121668-c583-4d3f-9726-e3c828ae6360' }];
      jest.spyOn(programRuleService, 'query').mockReturnValue(of(new HttpResponse({ body: programRuleCollection })));
      const additionalProgramRules = [programRule];
      const expectedCollection: IProgramRule[] = [...additionalProgramRules, ...programRuleCollection];
      jest.spyOn(programRuleService, 'addProgramRuleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(programRuleService.query).toHaveBeenCalled();
      expect(programRuleService.addProgramRuleToCollectionIfMissing).toHaveBeenCalledWith(
        programRuleCollection,
        ...additionalProgramRules.map(expect.objectContaining),
      );
      expect(comp.programRulesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrackedEntityAttribute query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '906a7845-855a-44cc-bead-f30388f01341' };
      programRuleAction.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: ITrackedEntityAttribute = { id: '604f1ec9-bc2b-4ac0-a54f-34dca3a8334a' };
      programRuleAction.dataElement = dataElement;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: '6d6526a5-42da-470c-826a-2d20680a2b94' }];
      jest.spyOn(trackedEntityAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: trackedEntityAttributeCollection })));
      const additionalTrackedEntityAttributes = [trackedEntityAttribute, dataElement];
      const expectedCollection: ITrackedEntityAttribute[] = [...additionalTrackedEntityAttributes, ...trackedEntityAttributeCollection];
      jest.spyOn(trackedEntityAttributeService, 'addTrackedEntityAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(trackedEntityAttributeService.query).toHaveBeenCalled();
      expect(trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        trackedEntityAttributeCollection,
        ...additionalTrackedEntityAttributes.map(expect.objectContaining),
      );
      expect(comp.trackedEntityAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OptionGroup query and add missing value', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const optionGroup: IOptionGroup = { id: '7cacb7d0-bd8a-4caf-a6f6-5eb06a01dff9' };
      programRuleAction.optionGroup = optionGroup;

      const optionGroupCollection: IOptionGroup[] = [{ id: '50e9cfc8-7f32-4bc2-9310-0840df2509f9' }];
      jest.spyOn(optionGroupService, 'query').mockReturnValue(of(new HttpResponse({ body: optionGroupCollection })));
      const additionalOptionGroups = [optionGroup];
      const expectedCollection: IOptionGroup[] = [...additionalOptionGroups, ...optionGroupCollection];
      jest.spyOn(optionGroupService, 'addOptionGroupToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRuleAction });
      comp.ngOnInit();

      expect(optionGroupService.query).toHaveBeenCalled();
      expect(optionGroupService.addOptionGroupToCollectionIfMissing).toHaveBeenCalledWith(
        optionGroupCollection,
        ...additionalOptionGroups.map(expect.objectContaining),
      );
      expect(comp.optionGroupsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programRuleAction: IProgramRuleAction = { id: 'CBA' };
      const project: IProject = { id: 27257 };
      programRuleAction.project = project;
      const createdBy: IDHISUser = { id: 'f6982664-5793-4e40-934d-d8b83472ca82' };
      programRuleAction.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '652ef981-de5f-4cf2-af39-f84eadcb851a' };
      programRuleAction.lastUpdatedBy = lastUpdatedBy;
      const programRule: IProgramRule = { id: '03feb7a6-9113-4f26-8302-0cba9d2a9a4a' };
      programRuleAction.programRule = programRule;
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '01104311-5a34-4525-9700-665189659371' };
      programRuleAction.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: ITrackedEntityAttribute = { id: 'd26bfea1-0426-4ca4-b4d6-19353a0c0ba4' };
      programRuleAction.dataElement = dataElement;
      const optionGroup: IOptionGroup = { id: 'fddeeb68-4531-48de-b11c-860cceece23f' };
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
