import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const dataset: IDataset = { id: 'CBA' };
      const project: IProject = { id: 19383 };
      dataset.project = project;

      const projectCollection: IProject[] = [{ id: 4140 }];
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
      const createdBy: IDHISUser = { id: '44a53b5f-8fdf-46f4-bca5-1307a7215e75' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '2a1fd84b-1331-4d4a-b86d-a7cabfbb6126' };
      dataset.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'a05837b6-6504-47be-b626-81ac9d273227' }];
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

    it('Should update editForm', () => {
      const dataset: IDataset = { id: 'CBA' };
      const project: IProject = { id: 14848 };
      dataset.project = project;
      const createdBy: IDHISUser = { id: 'de8cc686-717d-4498-9eed-59836a06304f' };
      dataset.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'e07cade4-f66b-4f46-8650-9ac6001e871d' };
      dataset.lastUpdatedBy = lastUpdatedBy;

      activatedRoute.data = of({ dataset });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
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
  });
});
