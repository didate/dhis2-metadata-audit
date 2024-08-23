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
import { IIndicator } from 'app/entities/indicator/indicator.model';
import { IndicatorService } from 'app/entities/indicator/service/indicator.service';
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
  let indicatorService: IndicatorService;
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
    indicatorService = TestBed.inject(IndicatorService);
    organisationUnitService = TestBed.inject(OrganisationUnitService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const project: IProject = { id: 18245 };
      dataset.project = project;

      const projectCollection: IProject[] = [{ id: 31864 }];
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
      const createdBy: IDHISUser = { id: 'f12e638a-48e9-4568-9055-4460cf99b72d' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '98a62221-ef27-488f-ba25-209cf5235da1' };
      dataset.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'ffcce77a-4e6e-4f47-be18-1715cd5495c5' }];
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
      const categoryCombo: ICategorycombo = { id: '8e8a984e-22cd-469f-8961-b4187ee868d1' };
      dataset.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: '77135af1-bc22-4855-aa41-4d1732b6dbc7' }];
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
      const dataSetElements: IDataelement[] = [{ id: '3213dc67-4cdd-4635-88a2-a3a365aa753c' }];
      dataset.dataSetElements = dataSetElements;

      const dataelementCollection: IDataelement[] = [{ id: '2392c4ea-7406-4b08-aed0-ce45ffb782cd' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [...dataSetElements];
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

    it('Should call Indicator query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const indicators: IIndicator[] = [{ id: '387de805-1213-4cb9-a5ec-c1ea499131cc' }];
      dataset.indicators = indicators;

      const indicatorCollection: IIndicator[] = [{ id: '2b3c7707-2de7-4a06-ad70-f895d16b239e' }];
      jest.spyOn(indicatorService, 'query').mockReturnValue(of(new HttpResponse({ body: indicatorCollection })));
      const additionalIndicators = [...indicators];
      const expectedCollection: IIndicator[] = [...additionalIndicators, ...indicatorCollection];
      jest.spyOn(indicatorService, 'addIndicatorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(indicatorService.query).toHaveBeenCalled();
      expect(indicatorService.addIndicatorToCollectionIfMissing).toHaveBeenCalledWith(
        indicatorCollection,
        ...additionalIndicators.map(expect.objectContaining),
      );
      expect(comp.indicatorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganisationUnit query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const organisationUnits: IOrganisationUnit[] = [{ id: '3efdaf39-150c-44ba-b749-05ed89cfd086' }];
      dataset.organisationUnits = organisationUnits;

      const organisationUnitCollection: IOrganisationUnit[] = [{ id: '850ce730-fc1e-4167-ac64-298adfd56504' }];
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
      const project: IProject = { id: 7481 };
      dataset.project = project;
      const createdBy: IDHISUser = { id: '32427d94-1528-4917-9a99-2a367631a424' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'eb5e1061-1da4-4642-806e-33b68704d2cd' };
      dataset.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '2fc0ff7f-58ed-4cc8-8b42-9ac8995c0071' };
      dataset.categoryCombo = categoryCombo;
      const dataSetElements: IDataelement = { id: '5ab1b9f5-a128-47db-a901-b581b65e79b5' };
      dataset.dataSetElements = [dataSetElements];
      const indicators: IIndicator = { id: '6e685e7e-8a11-46bd-8b33-6ba58a7953e0' };
      dataset.indicators = [indicators];
      const organisationUnits: IOrganisationUnit = { id: '4f290506-d254-43a2-a524-a0643bd2121e' };
      dataset.organisationUnits = [organisationUnits];

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.dataelementsSharedCollection).toContain(dataSetElements);
      expect(comp.indicatorsSharedCollection).toContain(indicators);
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

    describe('compareIndicator', () => {
      it('Should forward to indicatorService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(indicatorService, 'compareIndicator');
        comp.compareIndicator(entity, entity2);
        expect(indicatorService.compareIndicator).toHaveBeenCalledWith(entity, entity2);
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
