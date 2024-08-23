import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { OptionsetService } from 'app/entities/optionset/service/optionset.service';
import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';
import { TrackedEntityAttributeFormService } from './tracked-entity-attribute-form.service';

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
      imports: [TrackedEntityAttributeUpdateComponent],
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
      const project: IProject = { id: 3126 };
      trackedEntityAttribute.project = project;

      const projectCollection: IProject[] = [{ id: 21023 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '5cab32c0-bda8-4d85-8ed4-be0e19f418d4' };
      trackedEntityAttribute.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '785e8ece-de2e-44bf-bf16-dc7e07367f4d' };
      trackedEntityAttribute.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '305b12fa-0938-4943-92ce-a21f67677ca3' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Optionset query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const optionSet: IOptionset = { id: '9d6e5615-dfa9-44fe-a56b-73c76a6b28ba' };
      trackedEntityAttribute.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: 'eeba9b38-fba6-46bd-bd63-34466c832c66' }];
      jest.spyOn(optionsetService, 'query').mockReturnValue(of(new HttpResponse({ body: optionsetCollection })));
      const additionalOptionsets = [optionSet];
      const expectedCollection: IOptionset[] = [...additionalOptionsets, ...optionsetCollection];
      jest.spyOn(optionsetService, 'addOptionsetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(optionsetService.query).toHaveBeenCalled();
      expect(optionsetService.addOptionsetToCollectionIfMissing).toHaveBeenCalledWith(
        optionsetCollection,
        ...additionalOptionsets.map(expect.objectContaining),
      );
      expect(comp.optionsetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const project: IProject = { id: 2036 };
      trackedEntityAttribute.project = project;
      const createdBy: IDHISUser = { id: 'f13cdff1-6533-41f6-9c45-9cc46f8a2ff7' };
      trackedEntityAttribute.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '7dd94374-33fc-4883-8d71-7e7988af0f59' };
      trackedEntityAttribute.lastUpdatedBy = lastUpdatedBy;
      const optionSet: IOptionset = { id: '57cf291a-2c66-4f58-a197-82de059d3e2d' };
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
