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
      const project: IProject = { id: 13686 };
      indicator.project = project;

      const projectCollection: IProject[] = [{ id: 27851 }];
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
      const createdBy: IDHISUser = { id: '7b53ae9e-6127-4714-96c3-c8cf1737b3c2' };
      indicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '3208964f-83a9-4771-8b7b-c353a5788734' };
      indicator.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'a3773225-1648-4ead-99a1-fd6039dfeba4' }];
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
      const indicatorType: IIndicatortype = { id: '6061065e-0397-4a05-a87c-95ca1875d511' };
      indicator.indicatorType = indicatorType;

      const indicatortypeCollection: IIndicatortype[] = [{ id: 'a9698c4f-9bd8-44ef-8d61-2f0fe4d31630' }];
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
      const project: IProject = { id: 21563 };
      indicator.project = project;
      const createdBy: IDHISUser = { id: 'ee27e849-d409-48a1-9709-ee2413a6a569' };
      indicator.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'c807b2d6-8c83-4294-af13-b66e95b0faef' };
      indicator.lastUpdatedBy = lastUpdatedBy;
      const indicatorType: IIndicatortype = { id: '127169f2-9335-4a50-a670-af4d0d99881a' };
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
