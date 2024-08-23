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
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';
import { IOrganisationUnit } from 'app/entities/organisation-unit/organisation-unit.model';
import { OrganisationUnitService } from 'app/entities/organisation-unit/service/organisation-unit.service';
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
  let dataelementService: DataelementService;
  let organisationUnitService: OrganisationUnitService;

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
    dataelementService = TestBed.inject(DataelementService);
    organisationUnitService = TestBed.inject(OrganisationUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 640 };
      program.project = project;

      const projectCollection: IProject[] = [{ id: 6261 }];
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
      const createdBy: IDHISUser = { id: 'd6af8dd9-9d1b-4084-8ee2-e62b5f3f28d1' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '61a1e7da-cd60-49bf-9547-e9d68c93dada' };
      program.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '45aa4c86-8312-406b-93df-176270dec38e' }];
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
      const categoryCombo: ICategorycombo = { id: '987937c7-7f1c-4ba3-88d8-f0c930fb273e' };
      program.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: '0d57b622-f30d-41a0-8462-ffe8c97a6df2' }];
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

    it('Should call Dataelement query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const dataElements: IDataelement[] = [{ id: 'f328c42d-9b73-49c8-a740-59c088ecef4a' }];
      program.dataElements = dataElements;

      const dataelementCollection: IDataelement[] = [{ id: 'a9acd8e1-2201-4c7c-9934-baa9a2630a69' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [...dataElements];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining),
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganisationUnit query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const organisationUnits: IOrganisationUnit[] = [{ id: '0718eacf-dd2b-4afd-a10b-793f436380d4' }];
      program.organisationUnits = organisationUnits;

      const organisationUnitCollection: IOrganisationUnit[] = [{ id: '9997bae2-6c2e-4ffc-833c-8028d7631c65' }];
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

    it('Should update editForm', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 11162 };
      program.project = project;
      const createdBy: IDHISUser = { id: '533d5917-071f-4600-8f74-68c7c1cf683a' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '64379297-da2d-4098-ab6f-a7e89f70bfb5' };
      program.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: 'fe85e77e-7dec-4be7-97fa-eb0a3c211417' };
      program.categoryCombo = categoryCombo;
      const dataElements: IDataelement = { id: 'be5e2498-5d37-40ad-9e33-cee973b1201a' };
      program.dataElements = [dataElements];
      const organisationUnits: IOrganisationUnit = { id: '6237540c-8fee-488a-b034-92ffd5b2ed80' };
      program.organisationUnits = [organisationUnits];

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.dataelementsSharedCollection).toContain(dataElements);
      expect(comp.organisationUnitsSharedCollection).toContain(organisationUnits);
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

    describe('compareDataelement', () => {
      it('Should forward to dataelementService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(dataelementService, 'compareDataelement');
        comp.compareDataelement(entity, entity2);
        expect(dataelementService.compareDataelement).toHaveBeenCalledWith(entity, entity2);
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
  });
});
