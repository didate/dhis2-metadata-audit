import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrackedEntityAttributeFormService } from './tracked-entity-attribute-form.service';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';
import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { OptionsetService } from 'app/entities/optionset/service/optionset.service';

import { TrackedEntityAttributeUpdateComponent } from './tracked-entity-attribute-update.component';

describe('TrackedEntityAttribute Management Update Component', () => {
  let comp: TrackedEntityAttributeUpdateComponent;
  let fixture: ComponentFixture<TrackedEntityAttributeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trackedEntityAttributeFormService: TrackedEntityAttributeFormService;
  let trackedEntityAttributeService: TrackedEntityAttributeService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let optionsetService: OptionsetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrackedEntityAttributeUpdateComponent],
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
      .overrideTemplate(TrackedEntityAttributeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrackedEntityAttributeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trackedEntityAttributeFormService = TestBed.inject(TrackedEntityAttributeFormService);
    trackedEntityAttributeService = TestBed.inject(TrackedEntityAttributeService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    optionsetService = TestBed.inject(OptionsetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const project: IProject = { id: 7822 };
      trackedEntityAttribute.project = project;

      const projectCollection: IProject[] = [{ id: 11684 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'a0f47659-742e-472e-9e0c-1f118a6c2ecb' };
      trackedEntityAttribute.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '69ae6a38-a950-4d27-a0d5-d33401c0a36b' };
      trackedEntityAttribute.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'c7827181-939c-4290-aa26-213ba7b9514d' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Optionset query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const optionSet: IOptionset = { id: '81982c8a-d658-445f-931f-b82409fe7154' };
      trackedEntityAttribute.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: '2ede9b43-89a3-4384-857c-8934730aeda1' }];
      jest.spyOn(optionsetService, 'query').mockReturnValue(of(new HttpResponse({ body: optionsetCollection })));
      const additionalOptionsets = [optionSet];
      const expectedCollection: IOptionset[] = [...additionalOptionsets, ...optionsetCollection];
      jest.spyOn(optionsetService, 'addOptionsetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(optionsetService.query).toHaveBeenCalled();
      expect(optionsetService.addOptionsetToCollectionIfMissing).toHaveBeenCalledWith(
        optionsetCollection,
        ...additionalOptionsets.map(expect.objectContaining)
      );
      expect(comp.optionsetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const project: IProject = { id: 23801 };
      trackedEntityAttribute.project = project;
      const createdBy: IDHISUser = { id: 'ebe7d183-7192-4251-8209-28b64332c773' };
      trackedEntityAttribute.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'd2e89d52-2ae9-462a-bd25-8f742eaf39a5' };
      trackedEntityAttribute.lastUpdatedBy = lastUpdatedBy;
      const optionSet: IOptionset = { id: 'e91cd4d9-caae-4e24-90b5-1a834fa4fb70' };
      trackedEntityAttribute.optionSet = optionSet;

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.optionsetsSharedCollection).toContain(optionSet);
      expect(comp.trackedEntityAttribute).toEqual(trackedEntityAttribute);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrackedEntityAttribute>>();
      const trackedEntityAttribute = { id: 'ABC' };
      jest.spyOn(trackedEntityAttributeFormService, 'getTrackedEntityAttribute').mockReturnValue(trackedEntityAttribute);
      jest.spyOn(trackedEntityAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trackedEntityAttribute }));
      saveSubject.complete();

      // THEN
      expect(trackedEntityAttributeFormService.getTrackedEntityAttribute).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trackedEntityAttributeService.update).toHaveBeenCalledWith(expect.objectContaining(trackedEntityAttribute));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrackedEntityAttribute>>();
      const trackedEntityAttribute = { id: 'ABC' };
      jest.spyOn(trackedEntityAttributeFormService, 'getTrackedEntityAttribute').mockReturnValue({ id: null });
      jest.spyOn(trackedEntityAttributeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trackedEntityAttribute: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trackedEntityAttribute }));
      saveSubject.complete();

      // THEN
      expect(trackedEntityAttributeFormService.getTrackedEntityAttribute).toHaveBeenCalled();
      expect(trackedEntityAttributeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrackedEntityAttribute>>();
      const trackedEntityAttribute = { id: 'ABC' };
      jest.spyOn(trackedEntityAttributeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trackedEntityAttributeService.update).toHaveBeenCalled();
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

    describe('compareOptionset', () => {
      it('Should forward to optionsetService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(optionsetService, 'compareOptionset');
        comp.compareOptionset(entity, entity2);
        expect(optionsetService.compareOptionset).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
