import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IIndicatortype } from 'app/entities/indicatortype/indicatortype.model';
import { IndicatortypeService } from 'app/entities/indicatortype/service/indicatortype.service';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { DatasetService } from 'app/entities/dataset/service/dataset.service';
import { IIndicator } from '../indicator.model';
import { IndicatorService } from '../service/indicator.service';
import { IndicatorFormService } from './indicator-form.service';

import { IndicatorUpdateComponent } from './indicator-update.component';

describe('Indicator Management Update Component', () => {
  let comp: IndicatorUpdateComponent;
  let fixture: ComponentFixture<IndicatorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let indicatorFormService: IndicatorFormService;
  let indicatorService: IndicatorService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let indicatortypeService: IndicatortypeService;
  let datasetService: DatasetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [IndicatorUpdateComponent],
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
      .overrideTemplate(IndicatorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IndicatorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    indicatorFormService = TestBed.inject(IndicatorFormService);
    indicatorService = TestBed.inject(IndicatorService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    indicatortypeService = TestBed.inject(IndicatortypeService);
    datasetService = TestBed.inject(DatasetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const project: IProject = { id: 7862 };
      indicator.project = project;

      const projectCollection: IProject[] = [{ id: 4436 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'a345cb95-9d4c-4557-b8d4-5f6715531560' };
      indicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'b11e6f5d-6cc7-482f-884c-605bbbfe3b38' };
      indicator.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'd905a250-27d8-4904-97e9-f4bfbe937e0b' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Indicatortype query and add missing value', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const indicatorType: IIndicatortype = { id: '863d8e48-de8e-4f0a-844e-2849b93b82e4' };
      indicator.indicatorType = indicatorType;

      const indicatortypeCollection: IIndicatortype[] = [{ id: 'ca9ef481-52b0-4f0b-82cd-4825914629cf' }];
      jest.spyOn(indicatortypeService, 'query').mockReturnValue(of(new HttpResponse({ body: indicatortypeCollection })));
      const additionalIndicatortypes = [indicatorType];
      const expectedCollection: IIndicatortype[] = [...additionalIndicatortypes, ...indicatortypeCollection];
      jest.spyOn(indicatortypeService, 'addIndicatortypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      expect(indicatortypeService.query).toHaveBeenCalled();
      expect(indicatortypeService.addIndicatortypeToCollectionIfMissing).toHaveBeenCalledWith(
        indicatortypeCollection,
        ...additionalIndicatortypes.map(expect.objectContaining),
      );
      expect(comp.indicatortypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataset query and add missing value', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const datasets: IDataset[] = [{ id: '682aaf02-3740-4889-95fa-676908505f34' }];
      indicator.datasets = datasets;

      const datasetCollection: IDataset[] = [{ id: '37ebc250-1b4a-4dd8-ad5d-b628517aef94' }];
      jest.spyOn(datasetService, 'query').mockReturnValue(of(new HttpResponse({ body: datasetCollection })));
      const additionalDatasets = [...datasets];
      const expectedCollection: IDataset[] = [...additionalDatasets, ...datasetCollection];
      jest.spyOn(datasetService, 'addDatasetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      expect(datasetService.query).toHaveBeenCalled();
      expect(datasetService.addDatasetToCollectionIfMissing).toHaveBeenCalledWith(
        datasetCollection,
        ...additionalDatasets.map(expect.objectContaining),
      );
      expect(comp.datasetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const project: IProject = { id: 16258 };
      indicator.project = project;
      const createdBy: IDHISUser = { id: 'e6bc720c-e57c-497c-ba92-68daf77c0387' };
      indicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'eeeca545-38a6-4efb-b15d-d45c46d8c2a8' };
      indicator.lastUpdatedBy = lastUpdatedBy;
      const indicatorType: IIndicatortype = { id: '9340c15c-8328-4851-9fa1-1072a2e4e779' };
      indicator.indicatorType = indicatorType;
      const dataset: IDataset = { id: 'f99f1cc4-6779-4dc3-a350-7d95286636ea' };
      indicator.datasets = [dataset];

      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.indicatortypesSharedCollection).toContain(indicatorType);
      expect(comp.datasetsSharedCollection).toContain(dataset);
      expect(comp.indicator).toEqual(indicator);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicator>>();
      const indicator = { id: 'ABC' };
      jest.spyOn(indicatorFormService, 'getIndicator').mockReturnValue(indicator);
      jest.spyOn(indicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicator }));
      saveSubject.complete();

      // THEN
      expect(indicatorFormService.getIndicator).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(indicatorService.update).toHaveBeenCalledWith(expect.objectContaining(indicator));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicator>>();
      const indicator = { id: 'ABC' };
      jest.spyOn(indicatorFormService, 'getIndicator').mockReturnValue({ id: null });
      jest.spyOn(indicatorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicator: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: indicator }));
      saveSubject.complete();

      // THEN
      expect(indicatorFormService.getIndicator).toHaveBeenCalled();
      expect(indicatorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIndicator>>();
      const indicator = { id: 'ABC' };
      jest.spyOn(indicatorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(indicatorService.update).toHaveBeenCalled();
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

    describe('compareIndicatortype', () => {
      it('Should forward to indicatortypeService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(indicatortypeService, 'compareIndicatortype');
        comp.compareIndicatortype(entity, entity2);
        expect(indicatortypeService.compareIndicatortype).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDataset', () => {
      it('Should forward to datasetService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(datasetService, 'compareDataset');
        comp.compareDataset(entity, entity2);
        expect(datasetService.compareDataset).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
