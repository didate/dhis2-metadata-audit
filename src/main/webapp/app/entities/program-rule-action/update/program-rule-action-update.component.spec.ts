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
      const project: IProject = { id: 292 };
      programRuleAction.project = project;

      const projectCollection: IProject[] = [{ id: 27663 }];
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
      const createdBy: IDHISUser = { id: 'd2582bd6-2f84-4281-8943-d7e52ad1ed11' };
      programRuleAction.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'c86fea83-4278-4725-a7c2-41a09ff7d1fa' };
      programRuleAction.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '58f2a3de-dc9d-47c6-9d9a-591d5d2c3c66' }];
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
      const programRule: IProgramRule = { id: '11a1a7a3-f3c4-4bd0-a8d7-6ceafe84d086' };
      programRuleAction.programRule = programRule;

      const programRuleCollection: IProgramRule[] = [{ id: '509015ca-5530-4975-a9ae-1a5f90df7470' }];
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
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'acf032f7-e927-4736-9ea4-b3dd1b18a15f' };
      programRuleAction.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: ITrackedEntityAttribute = { id: '329a868d-d718-49be-8985-65b72dd28b1f' };
      programRuleAction.dataElement = dataElement;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: '4187dda6-afff-4c22-b70f-0b776bc4654c' }];
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
      const optionGroup: IOptionGroup = { id: '92b4ff1a-753c-4b72-899c-3eca48dbea66' };
      programRuleAction.optionGroup = optionGroup;

      const optionGroupCollection: IOptionGroup[] = [{ id: 'd9773713-7155-436b-a3c8-4da04403fed5' }];
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
      const project: IProject = { id: 32334 };
      programRuleAction.project = project;
      const createdBy: IDHISUser = { id: '1f3f6376-2ebe-473e-b212-244f4f65fff8' };
      programRuleAction.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'e87a7ff7-448c-4244-b38f-3866867f0745' };
      programRuleAction.lastUpdatedBy = lastUpdatedBy;
      const programRule: IProgramRule = { id: 'c2616d6e-62e3-4ef0-b8eb-ca8ca6dc5b06' };
      programRuleAction.programRule = programRule;
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: '03b868ee-f5a9-4c52-b5f7-5a0f8e114862' };
      programRuleAction.trackedEntityAttribute = trackedEntityAttribute;
      const dataElement: ITrackedEntityAttribute = { id: 'e3c493ad-72c6-4fd0-8478-c6aa9b944889' };
      programRuleAction.dataElement = dataElement;
      const optionGroup: IOptionGroup = { id: 'b82c4332-b99c-4815-885d-4a0de8d91127' };
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
