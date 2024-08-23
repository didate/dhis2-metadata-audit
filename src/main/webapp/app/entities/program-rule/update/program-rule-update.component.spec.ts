import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';
import { ProgramRuleFormService } from './program-rule-form.service';

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
      imports: [ProgramRuleUpdateComponent],
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
      const project: IProject = { id: 19338 };
      programRule.project = project;

      const projectCollection: IProject[] = [{ id: 18289 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const createdBy: IDHISUser = { id: 'dbf60bed-392c-4175-a7e4-de350439510d' };
      programRule.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '137deb08-e4b4-4b07-86c5-62cf97c1d717' };
      programRule.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '2c749d98-bfb7-45c1-a9cb-25096d583ae0' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const program: IProgram = { id: '70d21acb-5aa6-4281-a192-c253fd28f827' };
      programRule.program = program;

      const programCollection: IProgram[] = [{ id: 'e19492b8-6756-4c30-8b29-35af8f464f50' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [program];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ programRule });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const programRule: IProgramRule = { id: 'CBA' };
      const project: IProject = { id: 4728 };
      programRule.project = project;
      const createdBy: IDHISUser = { id: '93090d89-8475-473c-b932-0c3aff30ee4e' };
      programRule.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '00d0dfaa-2f20-4f1b-b236-ed3fc2d55c73' };
      programRule.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: 'dfe56571-ffbe-450a-b71a-c1426c23dda3' };
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
