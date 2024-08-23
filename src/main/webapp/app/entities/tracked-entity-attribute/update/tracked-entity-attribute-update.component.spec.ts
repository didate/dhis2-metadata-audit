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
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
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
  let programService: ProgramService;

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
    programService = TestBed.inject(ProgramService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const project: IProject = { id: 6425 };
      trackedEntityAttribute.project = project;

      const projectCollection: IProject[] = [{ id: 26161 }];
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
      const createdBy: IDHISUser = { id: '6cd8b814-b1bb-4c2f-84f8-0b550ccdf6e5' };
      trackedEntityAttribute.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '9ef6b195-0639-4c0d-bcbb-f2ad24338d64' };
      trackedEntityAttribute.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '6a562e17-faed-4119-ab96-2c20f906b594' }];
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
      const optionSet: IOptionset = { id: 'aacbb074-eb0d-4187-a48b-db45a9492b51' };
      trackedEntityAttribute.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: 'ff21b246-56da-4788-9da2-769c3a9b44bf' }];
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

    it('Should call Program query and add missing value', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const programs: IProgram[] = [{ id: 'c4fff314-4c8b-488b-bbee-d7bdc109112a' }];
      trackedEntityAttribute.programs = programs;

      const programCollection: IProgram[] = [{ id: '7cbead7f-3dfd-4dea-a3fb-2d7698466e97' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [...programs];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const trackedEntityAttribute: ITrackedEntityAttribute = { id: 'CBA' };
      const project: IProject = { id: 10284 };
      trackedEntityAttribute.project = project;
      const createdBy: IDHISUser = { id: '5a3e1daf-ab40-45d2-8e23-752e1d2b09fd' };
      trackedEntityAttribute.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '3b642165-50bf-4ecc-9ddd-9c08ff5c9198' };
      trackedEntityAttribute.lastUpdatedBy = lastUpdatedBy;
      const optionSet: IOptionset = { id: '141e7d33-1d64-41e5-a220-ec6d18942a7f' };
      trackedEntityAttribute.optionSet = optionSet;
      const program: IProgram = { id: 'd5deaff5-3681-4c50-beed-213a2a947353' };
      trackedEntityAttribute.programs = [program];

      activatedRoute.data = of({ trackedEntityAttribute });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.optionsetsSharedCollection).toContain(optionSet);
      expect(comp.programsSharedCollection).toContain(program);
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

    describe('compareProgram', () => {
      it('Should forward to programService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programService, 'compareProgram');
        comp.compareProgram(entity, entity2);
        expect(programService.compareProgram).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
