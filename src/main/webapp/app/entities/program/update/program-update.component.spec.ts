import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { CategorycomboService } from 'app/entities/categorycombo/service/categorycombo.service';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from 'app/entities/tracked-entity-attribute/service/tracked-entity-attribute.service';
import { IOrganisationUnit } from 'app/entities/organisation-unit/organisation-unit.model';
import { OrganisationUnitService } from 'app/entities/organisation-unit/service/organisation-unit.service';
import { IProgramIndicator } from 'app/entities/program-indicator/program-indicator.model';
import { ProgramIndicatorService } from 'app/entities/program-indicator/service/program-indicator.service';
import { IProgramStage } from 'app/entities/program-stage/program-stage.model';
import { ProgramStageService } from 'app/entities/program-stage/service/program-stage.service';
import { IProgram } from '../program.model';
import { ProgramService } from '../service/program.service';
import { ProgramFormService } from './program-form.service';

import { ProgramUpdateComponent } from './program-update.component';

describe('Program Management Update Component', () => {
  let comp: ProgramUpdateComponent;
  let fixture: ComponentFixture<ProgramUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programFormService: ProgramFormService;
  let programService: ProgramService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let categorycomboService: CategorycomboService;
  let trackedEntityAttributeService: TrackedEntityAttributeService;
  let organisationUnitService: OrganisationUnitService;
  let programIndicatorService: ProgramIndicatorService;
  let programStageService: ProgramStageService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProgramUpdateComponent],
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
      .overrideTemplate(ProgramUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programFormService = TestBed.inject(ProgramFormService);
    programService = TestBed.inject(ProgramService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    categorycomboService = TestBed.inject(CategorycomboService);
    trackedEntityAttributeService = TestBed.inject(TrackedEntityAttributeService);
    organisationUnitService = TestBed.inject(OrganisationUnitService);
    programIndicatorService = TestBed.inject(ProgramIndicatorService);
    programStageService = TestBed.inject(ProgramStageService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 14422 };
      program.project = project;

      const projectCollection: IProject[] = [{ id: 17721 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '43e61b27-1e24-4132-bee3-99df6cfa2a76' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '6aabfaf4-d141-4460-8037-4dcbbd1c0a8c' };
      program.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'b284d207-bcff-415c-a159-70aa22bdd31a' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categorycombo query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const categoryCombo: ICategorycombo = { id: 'd5a58890-9614-4830-9a3f-5b68d97e46ae' };
      program.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: 'ca4d1988-9368-41c9-a647-9bc7a012ab64' }];
      jest.spyOn(categorycomboService, 'query').mockReturnValue(of(new HttpResponse({ body: categorycomboCollection })));
      const additionalCategorycombos = [categoryCombo];
      const expectedCollection: ICategorycombo[] = [...additionalCategorycombos, ...categorycomboCollection];
      jest.spyOn(categorycomboService, 'addCategorycomboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(categorycomboService.query).toHaveBeenCalled();
      expect(categorycomboService.addCategorycomboToCollectionIfMissing).toHaveBeenCalledWith(
        categorycomboCollection,
        ...additionalCategorycombos.map(expect.objectContaining),
      );
      expect(comp.categorycombosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrackedEntityAttribute query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const programTrackedEntityAttributes: ITrackedEntityAttribute[] = [{ id: '12212171-d5d9-4308-a066-2ca3860e1055' }];
      program.programTrackedEntityAttributes = programTrackedEntityAttributes;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: 'de71d4e5-b355-48a6-9a24-b1936f97aa7a' }];
      jest.spyOn(trackedEntityAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: trackedEntityAttributeCollection })));
      const additionalTrackedEntityAttributes = [...programTrackedEntityAttributes];
      const expectedCollection: ITrackedEntityAttribute[] = [...additionalTrackedEntityAttributes, ...trackedEntityAttributeCollection];
      jest.spyOn(trackedEntityAttributeService, 'addTrackedEntityAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(trackedEntityAttributeService.query).toHaveBeenCalled();
      expect(trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        trackedEntityAttributeCollection,
        ...additionalTrackedEntityAttributes.map(expect.objectContaining),
      );
      expect(comp.trackedEntityAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganisationUnit query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const organisationUnits: IOrganisationUnit[] = [{ id: '1a206756-0517-45fa-9afd-8620f820853e' }];
      program.organisationUnits = organisationUnits;

      const organisationUnitCollection: IOrganisationUnit[] = [{ id: 'c5bb35ea-e236-489e-a4f0-c5348e158d9a' }];
      jest.spyOn(organisationUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationUnitCollection })));
      const additionalOrganisationUnits = [...organisationUnits];
      const expectedCollection: IOrganisationUnit[] = [...additionalOrganisationUnits, ...organisationUnitCollection];
      jest.spyOn(organisationUnitService, 'addOrganisationUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(organisationUnitService.query).toHaveBeenCalled();
      expect(organisationUnitService.addOrganisationUnitToCollectionIfMissing).toHaveBeenCalledWith(
        organisationUnitCollection,
        ...additionalOrganisationUnits.map(expect.objectContaining),
      );
      expect(comp.organisationUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProgramIndicator query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const programIndicators: IProgramIndicator[] = [{ id: 'bdfb4814-142e-49b9-a8a4-21b161df186b' }];
      program.programIndicators = programIndicators;

      const programIndicatorCollection: IProgramIndicator[] = [{ id: 'a512f3f3-e64f-45a1-b76b-2dabb03d6a79' }];
      jest.spyOn(programIndicatorService, 'query').mockReturnValue(of(new HttpResponse({ body: programIndicatorCollection })));
      const additionalProgramIndicators = [...programIndicators];
      const expectedCollection: IProgramIndicator[] = [...additionalProgramIndicators, ...programIndicatorCollection];
      jest.spyOn(programIndicatorService, 'addProgramIndicatorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(programIndicatorService.query).toHaveBeenCalled();
      expect(programIndicatorService.addProgramIndicatorToCollectionIfMissing).toHaveBeenCalledWith(
        programIndicatorCollection,
        ...additionalProgramIndicators.map(expect.objectContaining),
      );
      expect(comp.programIndicatorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProgramStage query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const programStages: IProgramStage[] = [{ id: 'c1c52751-c2b5-4a94-bc63-0bbb4728353f' }];
      program.programStages = programStages;

      const programStageCollection: IProgramStage[] = [{ id: '72e288e6-f6d9-424c-a63a-202d2cccad54' }];
      jest.spyOn(programStageService, 'query').mockReturnValue(of(new HttpResponse({ body: programStageCollection })));
      const additionalProgramStages = [...programStages];
      const expectedCollection: IProgramStage[] = [...additionalProgramStages, ...programStageCollection];
      jest.spyOn(programStageService, 'addProgramStageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(programStageService.query).toHaveBeenCalled();
      expect(programStageService.addProgramStageToCollectionIfMissing).toHaveBeenCalledWith(
        programStageCollection,
        ...additionalProgramStages.map(expect.objectContaining),
      );
      expect(comp.programStagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 4106 };
      program.project = project;
      const createdBy: IDHISUser = { id: '89a50de2-aa76-41b3-a93f-36ab9404d236' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '079d91e5-9685-4087-904d-0f7f5288e7ae' };
      program.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '9c4f11ce-26cb-41ab-821e-c490f19a3025' };
      program.categoryCombo = categoryCombo;
      const programTrackedEntityAttributes: ITrackedEntityAttribute = { id: 'ac675519-0a39-4c2e-9c68-2bc68ad066b1' };
      program.programTrackedEntityAttributes = [programTrackedEntityAttributes];
      const organisationUnits: IOrganisationUnit = { id: '688e5078-8d29-43f3-aa4a-b8d7115daeee' };
      program.organisationUnits = [organisationUnits];
      const programIndicators: IProgramIndicator = { id: '23de4254-f41f-4c87-a61f-1cdae52eb1fe' };
      program.programIndicators = [programIndicators];
      const programStage: IProgramStage = { id: '335cc3da-4460-46a3-9f8d-35eaf2e763b6' };
      program.programStages = [programStage];

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.trackedEntityAttributesSharedCollection).toContain(programTrackedEntityAttributes);
      expect(comp.organisationUnitsSharedCollection).toContain(organisationUnits);
      expect(comp.programIndicatorsSharedCollection).toContain(programIndicators);
      expect(comp.programStagesSharedCollection).toContain(programStage);
      expect(comp.program).toEqual(program);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgram>>();
      const program = { id: 'ABC' };
      jest.spyOn(programFormService, 'getProgram').mockReturnValue(program);
      jest.spyOn(programService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ program });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: program }));
      saveSubject.complete();

      // THEN
      expect(programFormService.getProgram).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programService.update).toHaveBeenCalledWith(expect.objectContaining(program));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgram>>();
      const program = { id: 'ABC' };
      jest.spyOn(programFormService, 'getProgram').mockReturnValue({ id: null });
      jest.spyOn(programService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ program: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: program }));
      saveSubject.complete();

      // THEN
      expect(programFormService.getProgram).toHaveBeenCalled();
      expect(programService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgram>>();
      const program = { id: 'ABC' };
      jest.spyOn(programService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ program });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programService.update).toHaveBeenCalled();
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

    describe('compareCategorycombo', () => {
      it('Should forward to categorycomboService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(categorycomboService, 'compareCategorycombo');
        comp.compareCategorycombo(entity, entity2);
        expect(categorycomboService.compareCategorycombo).toHaveBeenCalledWith(entity, entity2);
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

    describe('compareOrganisationUnit', () => {
      it('Should forward to organisationUnitService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(organisationUnitService, 'compareOrganisationUnit');
        comp.compareOrganisationUnit(entity, entity2);
        expect(organisationUnitService.compareOrganisationUnit).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProgramIndicator', () => {
      it('Should forward to programIndicatorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programIndicatorService, 'compareProgramIndicator');
        comp.compareProgramIndicator(entity, entity2);
        expect(programIndicatorService.compareProgramIndicator).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProgramStage', () => {
      it('Should forward to programStageService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programStageService, 'compareProgramStage');
        comp.compareProgramStage(entity, entity2);
        expect(programStageService.compareProgramStage).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
