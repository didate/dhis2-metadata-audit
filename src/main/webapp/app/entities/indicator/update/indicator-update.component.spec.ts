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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const project: IProject = { id: 7881 };
      indicator.project = project;

      const projectCollection: IProject[] = [{ id: 19322 }];
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
      const createdBy: IDHISUser = { id: 'b61d6394-d92f-4e00-abb4-761ff0ac7b28' };
      indicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'f1e7271a-f2b8-4c2a-800b-b52ac16cdf64' };
      indicator.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '07586614-8221-434f-aa7f-e2e573827005' }];
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
      const indicatorType: IIndicatortype = { id: 'a5987cb6-f907-4148-812c-adba778c1459' };
      indicator.indicatorType = indicatorType;

      const indicatortypeCollection: IIndicatortype[] = [{ id: '6c1868db-c905-44df-a214-010372e98be5' }];
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

    it('Should update editForm', () => {
      const indicator: IIndicator = { id: 'CBA' };
      const project: IProject = { id: 20667 };
      indicator.project = project;
      const createdBy: IDHISUser = { id: '7dc9f13e-72bb-44d5-ac7c-9ac6219ad747' };
      indicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '6b84e3a7-81bf-4560-bfe2-cc753250fafe' };
      indicator.lastUpdatedBy = lastUpdatedBy;
      const indicatorType: IIndicatortype = { id: '0a0dae2c-e86f-459f-963f-fa2eb8cf8c83' };
      indicator.indicatorType = indicatorType;

      activatedRoute.data = of({ indicator });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.indicatortypesSharedCollection).toContain(indicatorType);
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
  });
});
