import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from '../program.model';
import { ProgramService } from '../service/program.service';
import { ProgramFormService } from './program-form.service';

import { ProgramUpdateComponent } from './program-update.component';

describe('Program Management Update Component', () => {
  let comp: ProgramUpdateComponent;
  let fixture: ComponentFixture<ProgramUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programFormService: ProgramFormService;
  let programService: ProgramService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProgramUpdateComponent],
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
      .overrideTemplate(ProgramUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programFormService = TestBed.inject(ProgramFormService);
    programService = TestBed.inject(ProgramService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 18442 };
      program.project = project;

      const projectCollection: IProject[] = [{ id: 6962 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const program: IProgram = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '74bdfbff-00c8-4573-bf9f-250c4404c66e' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '5fe5a64b-1157-46f8-8802-a1347308bc5c' };
      program.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '105a7b7e-1d46-421b-8ca6-abc371d6e962' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const program: IProgram = { id: 'CBA' };
      const project: IProject = { id: 17488 };
      program.project = project;
      const createdBy: IDHISUser = { id: '2bd20d08-cb66-4beb-9ff0-da93d76bd041' };
      program.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '46d6efe5-3774-45cf-8f88-7947a5324f48' };
      program.lastUpdatedBy = lastUpdatedBy;

      activatedRoute.data = of({ program });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.program).toEqual(program);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgram>>();
      const program = { id: 'ABC' };
      jest.spyOn(programFormService, 'getProgram').mockReturnValue(program);
      jest.spyOn(programService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ program });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: program }));
      saveSubject.complete();

      // THEN
      expect(programFormService.getProgram).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programService.update).toHaveBeenCalledWith(expect.objectContaining(program));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgram>>();
      const program = { id: 'ABC' };
      jest.spyOn(programFormService, 'getProgram').mockReturnValue({ id: null });
      jest.spyOn(programService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ program: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: program }));
      saveSubject.complete();

      // THEN
      expect(programFormService.getProgram).toHaveBeenCalled();
      expect(programService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgram>>();
      const program = { id: 'ABC' };
      jest.spyOn(programService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ program });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programService.update).toHaveBeenCalled();
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
