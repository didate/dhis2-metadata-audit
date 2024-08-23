import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DatasetFormService } from './dataset-form.service';
import { DatasetService } from '../service/dataset.service';
import { IDataset } from '../dataset.model';
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
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DatasetUpdateComponent],
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
      const project: IProject = { id: 30664 };
      dataset.project = project;

      const projectCollection: IProject[] = [{ id: 67728 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '73d95213-3d49-43b9-95a1-567b17941712' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '64a67c6d-9562-4066-b734-309f2c013fc3' };
      dataset.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '717e1459-5f94-4fa4-bf45-b4b59dafec1a' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categorycombo query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const categoryCombo: ICategorycombo = { id: '39c76803-2ec2-4772-a2eb-7885cdec1df0' };
      dataset.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: 'f47e5641-4c4a-4471-b878-412d660183b9' }];
      jest.spyOn(categorycomboService, 'query').mockReturnValue(of(new HttpResponse({ body: categorycomboCollection })));
      const additionalCategorycombos = [categoryCombo];
      const expectedCollection: ICategorycombo[] = [...additionalCategorycombos, ...categorycomboCollection];
      jest.spyOn(categorycomboService, 'addCategorycomboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(categorycomboService.query).toHaveBeenCalled();
      expect(categorycomboService.addCategorycomboToCollectionIfMissing).toHaveBeenCalledWith(
        categorycomboCollection,
        ...additionalCategorycombos.map(expect.objectContaining)
      );
      expect(comp.categorycombosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataelement query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const dataSetElements: IDataelement[] = [{ id: 'd7a97b6e-6760-4628-993c-53fdffd7fcf1' }];
      dataset.dataSetElements = dataSetElements;

      const dataelementCollection: IDataelement[] = [{ id: '246763d2-a568-4659-b960-286e68a838c1' }];
      jest.spyOn(dataelementService, 'query').mockReturnValue(of(new HttpResponse({ body: dataelementCollection })));
      const additionalDataelements = [...dataSetElements];
      const expectedCollection: IDataelement[] = [...additionalDataelements, ...dataelementCollection];
      jest.spyOn(dataelementService, 'addDataelementToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(dataelementService.query).toHaveBeenCalled();
      expect(dataelementService.addDataelementToCollectionIfMissing).toHaveBeenCalledWith(
        dataelementCollection,
        ...additionalDataelements.map(expect.objectContaining)
      );
      expect(comp.dataelementsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Indicator query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const indicators: IIndicator[] = [{ id: '7e3f08e7-3cb5-4bc8-978d-faeba0c8da1b' }];
      dataset.indicators = indicators;

      const indicatorCollection: IIndicator[] = [{ id: 'd60d1787-49c9-4a4a-9867-d55700b4d265' }];
      jest.spyOn(indicatorService, 'query').mockReturnValue(of(new HttpResponse({ body: indicatorCollection })));
      const additionalIndicators = [...indicators];
      const expectedCollection: IIndicator[] = [...additionalIndicators, ...indicatorCollection];
      jest.spyOn(indicatorService, 'addIndicatorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(indicatorService.query).toHaveBeenCalled();
      expect(indicatorService.addIndicatorToCollectionIfMissing).toHaveBeenCalledWith(
        indicatorCollection,
        ...additionalIndicators.map(expect.objectContaining)
      );
      expect(comp.indicatorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call OrganisationUnit query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const organisationUnits: IOrganisationUnit[] = [{ id: 'fc419e68-d4a1-4091-af5b-d9d1fe816caf' }];
      dataset.organisationUnits = organisationUnits;

      const organisationUnitCollection: IOrganisationUnit[] = [{ id: '5db784d9-a061-474c-8b02-0b62403256f3' }];
      jest.spyOn(organisationUnitService, 'query').mockReturnValue(of(new HttpResponse({ body: organisationUnitCollection })));
      const additionalOrganisationUnits = [...organisationUnits];
      const expectedCollection: IOrganisationUnit[] = [...additionalOrganisationUnits, ...organisationUnitCollection];
      jest.spyOn(organisationUnitService, 'addOrganisationUnitToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(organisationUnitService.query).toHaveBeenCalled();
      expect(organisationUnitService.addOrganisationUnitToCollectionIfMissing).toHaveBeenCalledWith(
        organisationUnitCollection,
        ...additionalOrganisationUnits.map(expect.objectContaining)
      );
      expect(comp.organisationUnitsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataset: IDataset = { id: 'CBA' };
      const project: IProject = { id: 18531 };
      dataset.project = project;
      const createdBy: IDHISUser = { id: '0b4807b9-c688-4a96-8d84-dd5bf8243dc8' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'ceb4a50a-6b2d-4844-9988-b468ce89c554' };
      dataset.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '95f43e2c-d330-47f3-993a-70b30a55ea57' };
      dataset.categoryCombo = categoryCombo;
      const dataSetElements: IDataelement = { id: '0d34d1eb-5568-478e-a9cd-f7428404d4e7' };
      dataset.dataSetElements = [dataSetElements];
      const indicators: IIndicator = { id: '63543dd0-2f3d-4320-a38f-cbd659ccd6ad' };
      dataset.indicators = [indicators];
      const organisationUnits: IOrganisationUnit = { id: 'df88237f-6f57-4488-975f-338ffcbe275a' };
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
