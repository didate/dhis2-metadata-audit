import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProgramRuleFormService } from './program-rule-form.service';
import { ProgramRuleService } from '../service/program-rule.service';
import { IProgramRule } from '../program-rule.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';

import { ProgramRuleUpdateComponent } from './program-rule-update.component';

describe('ProgramRule Management Update Component', () => {
  let comp: ProgramRuleUpdateComponent;
  let fixture: ComponentFixture<ProgramRuleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let programRuleFormService: ProgramRuleFormService;
  let programRuleService: ProgramRuleService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let programService: ProgramService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProgramRuleUpdateComponent],
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
      .overrideTemplate(ProgramRuleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProgramRuleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    programRuleFormService = TestBed.inject(ProgramRuleFormService);
    programRuleService = TestBed.inject(ProgramRuleService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    programService = TestBed.inject(ProgramService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const project: IProject = { id: 68597 };
      programRule.project = project;

      const projectCollection: IProject[] = [{ id: 20674 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '3d498692-fe89-4579-a930-5c4278bd76f4' };
      programRule.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '7ac6ee4a-4041-4f59-849d-e1eae2e5168a' };
      programRule.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '814b7e9c-6655-462c-9ae2-4b18a8db18fa' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const program: IProgram = { id: 'b5fe7259-1f76-46a8-983a-7d6dfeaa7342' };
      programRule.program = program;

      const programCollection: IProgram[] = [{ id: 'afc9605f-a50c-4825-882b-b2e97314a144' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining)
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const project: IProject = { id: 4083 };
      programRule.project = project;
      const createdBy: IDHISUser = { id: '18d6f75f-4fc7-41aa-b67e-2e3d922f124c' };
      programRule.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '3d234333-36b3-4b34-8cf1-87614558d14d' };
      programRule.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '82e82b92-cc16-45dd-add1-d22238a38c05' };
      programRule.program = program;

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.programRule).toEqual(programRule);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRule>>();
      const programRule = { id: 'ABC' };
      jest.spyOn(programRuleFormService, 'getProgramRule').mockReturnValue(programRule);
      jest.spyOn(programRuleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programRule }));
      saveSubject.complete();

      // THEN
      expect(programRuleFormService.getProgramRule).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(programRuleService.update).toHaveBeenCalledWith(expect.objectContaining(programRule));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRule>>();
      const programRule = { id: 'ABC' };
      jest.spyOn(programRuleFormService, 'getProgramRule').mockReturnValue({ id: null });
      jest.spyOn(programRuleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRule: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: programRule }));
      saveSubject.complete();

      // THEN
      expect(programRuleFormService.getProgramRule).toHaveBeenCalled();
      expect(programRuleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProgramRule>>();
      const programRule = { id: 'ABC' };
      jest.spyOn(programRuleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(programRuleService.update).toHaveBeenCalled();
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
