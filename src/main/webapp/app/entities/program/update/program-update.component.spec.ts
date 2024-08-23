import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramFormService } from './program-form.service';
import { ProgramService } from '../service/program.service';
import { IProgram } from '../program.model';
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
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramUpdateComponent],
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
      const project: IProject = { id: 27420 };
      program.project = project;

      const projectCollection: IProject[] = [{ id: 10294 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'ba7a63ef-5d85-4475-8e3f-27ca97a06d8e' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'ed2b1248-8f96-4974-a1eb-cb0072d62049' };
      program.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '4de8b075-e204-47cf-9de7-0c5beffd5fdf' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categorycombo query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const categoryCombo: ICategorycombo = { id: '70f9cbf0-8a37-4adb-873c-7c1bdb93f211' };
      program.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: '6fd7b70b-1689-42ca-b51d-4f68e616d8b3' }];
      jest.spyOn(categorycomboService, 'query').mockReturnValue(of(new HttpResponse({ body: categorycomboCollection })));
      const additionalCategorycombos = [categoryCombo];
      const expectedCollection: ICategorycombo[] = [...additionalCategorycombos, ...categorycomboCollection];
      jest.spyOn(categorycomboService, 'addCategorycomboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(categorycomboService.query).toHaveBeenCalled();
      expect(categorycomboService.addCategorycomboToCollectionIfMissing).toHaveBeenCalledWith(
        categorycomboCollection,
        ...additionalCategorycombos.map(expect.objectContaining)
      );
      expect(comp.categorycombosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrackedEntityAttribute query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const programTrackedEntityAttributes: ITrackedEntityAttribute[] = [{ id: 'f000ff53-e816-46fd-aa55-38ea070eab40' }];
      program.programTrackedEntityAttributes = programTrackedEntityAttributes;

      const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [{ id: '335d4520-fe63-4051-b993-9a1bcd56e835' }];
      jest.spyOn(trackedEntityAttributeService, 'query').mockReturnValue(of(new HttpResponse({ body: trackedEntityAttributeCollection })));
      const additionalTrackedEntityAttributes = [...programTrackedEntityAttributes];
      const expectedCollection: ITrackedEntityAttribute[] = [...additionalTrackedEntityAttributes, ...trackedEntityAttributeCollection];
      jest.spyOn(trackedEntityAttributeService, 'addTrackedEntityAttributeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(trackedEntityAttributeService.query).toHaveBeenCalled();
      expect(trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing).toHaveBeenCalledWith(
        trackedEntityAttributeCollection,
        ...additionalTrackedEntityAttributes.map(expect.objectContaining)
      );
      expect(comp.trackedEntityAttributesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganisationUnit query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const organisationUnits: IOrganisationUnit[] = [{ id: '3e03b83f-e805-4b23-a5d7-681ba4ea6eca' }];
      program.organisationUnits = organisationUnits;

      const organisationUnitCollection: IOrganisationUnit[] = [{ id: '60079ffd-bb84-4f69-846a-8aa6cc769f70' }];
      jest.spyOn(organisationUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationUnitCollection })));
      const additionalOrganisationUnits = [...organisationUnits];
      const expectedCollection: IOrganisationUnit[] = [...additionalOrganisationUnits, ...organisationUnitCollection];
      jest.spyOn(organisationUnitService, 'addOrganisationUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(organisationUnitService.query).toHaveBeenCalled();
      expect(organisationUnitService.addOrganisationUnitToCollectionIfMissing).toHaveBeenCalledWith(
        organisationUnitCollection,
        ...additionalOrganisationUnits.map(expect.objectContaining)
      );
      expect(comp.organisationUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProgramIndicator query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const programIndicators: IProgramIndicator[] = [{ id: '1c109e3a-33a4-4eed-9c2e-7441b117cce2' }];
      program.programIndicators = programIndicators;

      const programIndicatorCollection: IProgramIndicator[] = [{ id: 'a8424510-b9b7-4e3c-b325-09ee0df0a7bf' }];
      jest.spyOn(programIndicatorService, 'query').mockReturnValue(of(new HttpResponse({ body: programIndicatorCollection })));
      const additionalProgramIndicators = [...programIndicators];
      const expectedCollection: IProgramIndicator[] = [...additionalProgramIndicators, ...programIndicatorCollection];
      jest.spyOn(programIndicatorService, 'addProgramIndicatorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(programIndicatorService.query).toHaveBeenCalled();
      expect(programIndicatorService.addProgramIndicatorToCollectionIfMissing).toHaveBeenCalledWith(
        programIndicatorCollection,
        ...additionalProgramIndicators.map(expect.objectContaining)
      );
      expect(comp.programIndicatorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ProgramStage query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const programStages: IProgramStage[] = [{ id: 'b21763e3-b304-47a9-adba-e2e24aca4d2a' }];
      program.programStages = programStages;

      const programStageCollection: IProgramStage[] = [{ id: '0de0c74d-3b89-465d-8a5d-0e8c9f71eeee' }];
      jest.spyOn(programStageService, 'query').mockReturnValue(of(new HttpResponse({ body: programStageCollection })));
      const additionalProgramStages = [...programStages];
      const expectedCollection: IProgramStage[] = [...additionalProgramStages, ...programStageCollection];
      jest.spyOn(programStageService, 'addProgramStageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(programStageService.query).toHaveBeenCalled();
      expect(programStageService.addProgramStageToCollectionIfMissing).toHaveBeenCalledWith(
        programStageCollection,
        ...additionalProgramStages.map(expect.objectContaining)
      );
      expect(comp.programStagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 94308 };
      program.project = project;
      const createdBy: IDHISUser = { id: '2f978a00-b932-45de-a2cd-17b2b879e568' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'a24180ea-1bb5-4979-8962-0693149bac87' };
      program.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '5ec184a1-97ea-4551-9a60-a36606cd8248' };
      program.categoryCombo = categoryCombo;
      const programTrackedEntityAttributes: ITrackedEntityAttribute = { id: 'aada4969-3acd-4db2-90de-b46c9f176326' };
      program.programTrackedEntityAttributes = [programTrackedEntityAttributes];
      const organisationUnits: IOrganisationUnit = { id: '1acf834a-dcfd-44d0-93d6-904b4555633b' };
      program.organisationUnits = [organisationUnits];
      const programIndicators: IProgramIndicator = { id: '34b7fc06-f219-4dd6-a037-54e47300d5e1' };
      program.programIndicators = [programIndicators];
      const programStage: IProgramStage = { id: '80e77129-ffe5-4e81-bf21-8cf6248a1b81' };
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
