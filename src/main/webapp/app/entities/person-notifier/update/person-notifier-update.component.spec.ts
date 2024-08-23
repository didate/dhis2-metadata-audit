import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { PersonNotifierService } from '../service/person-notifier.service';
import { IPersonNotifier } from '../person-notifier.model';
import { PersonNotifierFormService } from './person-notifier-form.service';

import { PersonNotifierUpdateComponent } from './person-notifier-update.component';

describe('PersonNotifier Management Update Component', () => {
  let comp: PersonNotifierUpdateComponent;
  let fixture: ComponentFixture<PersonNotifierUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let personNotifierFormService: PersonNotifierFormService;
  let personNotifierService: PersonNotifierService;
  let projectService: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PersonNotifierUpdateComponent],
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
      .overrideTemplate(PersonNotifierUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PersonNotifierUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    personNotifierFormService = TestBed.inject(PersonNotifierFormService);
    personNotifierService = TestBed.inject(PersonNotifierService);
    projectService = TestBed.inject(ProjectService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const personNotifier: IPersonNotifier = { id: 456 };
      const project: IProject = { id: 9889 };
      personNotifier.project = project;

      const projectCollection: IProject[] = [{ id: 17125 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ personNotifier });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const personNotifier: IPersonNotifier = { id: 456 };
      const project: IProject = { id: 3880 };
      personNotifier.project = project;

      activatedRoute.data = of({ personNotifier });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.personNotifier).toEqual(personNotifier);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonNotifier>>();
      const personNotifier = { id: 123 };
      jest.spyOn(personNotifierFormService, 'getPersonNotifier').mockReturnValue(personNotifier);
      jest.spyOn(personNotifierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personNotifier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personNotifier }));
      saveSubject.complete();

      // THEN
      expect(personNotifierFormService.getPersonNotifier).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(personNotifierService.update).toHaveBeenCalledWith(expect.objectContaining(personNotifier));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonNotifier>>();
      const personNotifier = { id: 123 };
      jest.spyOn(personNotifierFormService, 'getPersonNotifier').mockReturnValue({ id: null });
      jest.spyOn(personNotifierService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personNotifier: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: personNotifier }));
      saveSubject.complete();

      // THEN
      expect(personNotifierFormService.getPersonNotifier).toHaveBeenCalled();
      expect(personNotifierService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPersonNotifier>>();
      const personNotifier = { id: 123 };
      jest.spyOn(personNotifierService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ personNotifier });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(personNotifierService.update).toHaveBeenCalled();
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
  });
});
