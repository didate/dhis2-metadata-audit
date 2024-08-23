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
import { IDataset } from '../dataset.model';
import { DatasetService } from '../service/dataset.service';
import { DatasetFormService } from './dataset-form.service';

import { DatasetUpdateComponent } from './dataset-update.component';

describe('Dataset Management Update Component', () => {
  let comp: DatasetUpdateComponent;
  let fixture: ComponentFixture<DatasetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let datasetFormService: DatasetFormService;
  let datasetService: DatasetService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let categorycomboService: CategorycomboService;
  let dataelementService: DataelementService;
  let organisationUnitService: OrganisationUnitService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DatasetUpdateComponent],
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
      .overrideTemplate(DatasetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DatasetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    datasetFormService = TestBed.inject(DatasetFormService);
    datasetService = TestBed.inject(DatasetService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    categorycomboService = TestBed.inject(CategorycomboService);
    dataelementService = TestBed.inject(DataelementService);
    organisationUnitService = TestBed.inject(OrganisationUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const project: IProject = { id: 28836 };
      dataset.project = project;

      const projectCollection: IProject[] = [{ id: 18528 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'ed3ea4ca-6e23-4f72-ad70-37fa4d8274b8' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '31c3a9d4-9f7e-42f6-9bb9-c795fa8bbaf0' };
      dataset.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'aa2db870-0da3-43d6-86d8-6160a4260a83' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categorycombo query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const categoryCombo: ICategorycombo = { id: '06afac94-963b-4ca5-b380-2243597c3a38' };
      dataset.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: '8567b2bb-4963-4ff3-b999-143a5081ffb3' }];
      jest.spyOn(categorycomboService, 'query').mockReturnValue(of(new HttpResponse({ body: categorycomboCollection })));
      const additionalCategorycombos = [categoryCombo];
      const expectedCollection: ICategorycombo[] = [...additionalCategorycombos, ...categorycomboCollection];
      jest.spyOn(categorycomboService, 'addCategorycomboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(categorycomboService.query).toHaveBeenCalled();
      expect(categorycomboService.addCategorycomboToCollectionIfMissing).toHaveBeenCalledWith(
        categorycomboCollection,
        ...additionalCategorycombos.map(expect.objectContaining),
      );
      expect(comp.categorycombosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataelement query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const dataElements: IDataelement[] = [{ id: '8045ec4f-c70b-41b6-9b77-b51d0b8a4424' }];
      dataset.dataElements = dataElements;

      const dataelementCollection: IDataelement[] = [{ id: 'd263dc63-9be6-4862-96b4-76ad49e2315e' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [...dataElements];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining),
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganisationUnit query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const organisationUnits: IOrganisationUnit[] = [{ id: '648b2879-daae-4ee9-98b7-699b57e53b2c' }];
      dataset.organisationUnits = organisationUnits;

      const organisationUnitCollection: IOrganisationUnit[] = [{ id: '08617446-883e-4aef-9f8d-3a219acb293c' }];
      jest.spyOn(organisationUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationUnitCollection })));
      const additionalOrganisationUnits = [...organisationUnits];
      const expectedCollection: IOrganisationUnit[] = [...additionalOrganisationUnits, ...organisationUnitCollection];
      jest.spyOn(organisationUnitService, 'addOrganisationUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(organisationUnitService.query).toHaveBeenCalled();
      expect(organisationUnitService.addOrganisationUnitToCollectionIfMissing).toHaveBeenCalledWith(
        organisationUnitCollection,
        ...additionalOrganisationUnits.map(expect.objectContaining),
      );
      expect(comp.organisationUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataset: IDataset = { id: 'CBA' };
      const project: IProject = { id: 17688 };
      dataset.project = project;
      const createdBy: IDHISUser = { id: '95cbc910-07b5-4a06-8136-e4a91eb9b658' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '20e38d38-ea74-4e65-b48f-1b3f283fa677' };
      dataset.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '45410a85-0660-4e25-9360-bf0366fdd52f' };
      dataset.categoryCombo = categoryCombo;
      const dataElements: IDataelement = { id: '107696dc-afb9-4b74-8af3-b9ffa633d611' };
      dataset.dataElements = [dataElements];
      const organisationUnits: IOrganisationUnit = { id: 'a3de7139-dd2a-473d-95ef-949ee20c5002' };
      dataset.organisationUnits = [organisationUnits];

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.dataelementsSharedCollection).toContain(dataElements);
      expect(comp.organisationUnitsSharedCollection).toContain(organisationUnits);
      expect(comp.dataset).toEqual(dataset);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataset>>();
      const dataset = { id: 'ABC' };
      jest.spyOn(datasetFormService, 'getDataset').mockReturnValue(dataset);
      jest.spyOn(datasetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataset }));
      saveSubject.complete();

      // THEN
      expect(datasetFormService.getDataset).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(datasetService.update).toHaveBeenCalledWith(expect.objectContaining(dataset));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataset>>();
      const dataset = { id: 'ABC' };
      jest.spyOn(datasetFormService, 'getDataset').mockReturnValue({ id: null });
      jest.spyOn(datasetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataset: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataset }));
      saveSubject.complete();

      // THEN
      expect(datasetFormService.getDataset).toHaveBeenCalled();
      expect(datasetService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataset>>();
      const dataset = { id: 'ABC' };
      jest.spyOn(datasetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(datasetService.update).toHaveBeenCalled();
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
