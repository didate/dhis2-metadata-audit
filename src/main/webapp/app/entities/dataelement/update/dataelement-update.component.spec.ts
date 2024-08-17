import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { CategorycomboService } from 'app/entities/categorycombo/service/categorycombo.service';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { OptionsetService } from 'app/entities/optionset/service/optionset.service';
import { IDataelement } from '../dataelement.model';
import { DataelementService } from '../service/dataelement.service';
import { DataelementFormService } from './dataelement-form.service';

import { DataelementUpdateComponent } from './dataelement-update.component';

describe('Dataelement Management Update Component', () => {
  let comp: DataelementUpdateComponent;
  let fixture: ComponentFixture<DataelementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataelementFormService: DataelementFormService;
  let dataelementService: DataelementService;
  let projectService: ProjectService;
  let dHISUserService: DHISUserService;
  let categorycomboService: CategorycomboService;
  let optionsetService: OptionsetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DataelementUpdateComponent],
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
      .overrideTemplate(DataelementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataelementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dataelementFormService = TestBed.inject(DataelementFormService);
    dataelementService = TestBed.inject(DataelementService);
    projectService = TestBed.inject(ProjectService);
    dHISUserService = TestBed.inject(DHISUserService);
    categorycomboService = TestBed.inject(CategorycomboService);
    optionsetService = TestBed.inject(OptionsetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 8946 };
      dataelement.project = project;

      const projectCollection: IProject[] = [{ id: 31988 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining),
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '5018242e-cd72-4e9c-b3b8-e8298e302009' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'e00ebe9e-7e58-4879-b649-3af4779f5aea' };
      dataelement.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '7c21e89d-4333-4aff-ad7a-c329346dbf45' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categorycombo query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const categoryCombo: ICategorycombo = { id: 'a4bea261-6db3-41ca-b4af-6d019d631abf' };
      dataelement.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: 'de95bec2-1c67-4cfa-b2cf-930e55d1845d' }];
      jest.spyOn(categorycomboService, 'query').mockReturnValue(of(new HttpResponse({ body: categorycomboCollection })));
      const additionalCategorycombos = [categoryCombo];
      const expectedCollection: ICategorycombo[] = [...additionalCategorycombos, ...categorycomboCollection];
      jest.spyOn(categorycomboService, 'addCategorycomboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(categorycomboService.query).toHaveBeenCalled();
      expect(categorycomboService.addCategorycomboToCollectionIfMissing).toHaveBeenCalledWith(
        categorycomboCollection,
        ...additionalCategorycombos.map(expect.objectContaining),
      );
      expect(comp.categorycombosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Optionset query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const optionSet: IOptionset = { id: '86cef29f-afcd-45aa-86b6-dfe4b52540b8' };
      dataelement.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: 'f462a755-c07c-4ea4-8fb3-feef5b7988c1' }];
      jest.spyOn(optionsetService, 'query').mockReturnValue(of(new HttpResponse({ body: optionsetCollection })));
      const additionalOptionsets = [optionSet];
      const expectedCollection: IOptionset[] = [...additionalOptionsets, ...optionsetCollection];
      jest.spyOn(optionsetService, 'addOptionsetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(optionsetService.query).toHaveBeenCalled();
      expect(optionsetService.addOptionsetToCollectionIfMissing).toHaveBeenCalledWith(
        optionsetCollection,
        ...additionalOptionsets.map(expect.objectContaining),
      );
      expect(comp.optionsetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 11639 };
      dataelement.project = project;
      const createdBy: IDHISUser = { id: '90027be9-54a0-43ed-89d3-61cae3bdb466' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '80bab221-5df1-4131-87c9-198cfd59b501' };
      dataelement.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: 'e66f8176-68d6-4d61-9edf-63b000ea42c2' };
      dataelement.categoryCombo = categoryCombo;
      const optionSet: IOptionset = { id: 'b42c54d4-7de1-4349-8914-4eb6879406bb' };
      dataelement.optionSet = optionSet;

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.optionsetsSharedCollection).toContain(optionSet);
      expect(comp.dataelement).toEqual(dataelement);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataelement>>();
      const dataelement = { id: 'ABC' };
      jest.spyOn(dataelementFormService, 'getDataelement').mockReturnValue(dataelement);
      jest.spyOn(dataelementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataelement }));
      saveSubject.complete();

      // THEN
      expect(dataelementFormService.getDataelement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dataelementService.update).toHaveBeenCalledWith(expect.objectContaining(dataelement));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataelement>>();
      const dataelement = { id: 'ABC' };
      jest.spyOn(dataelementFormService, 'getDataelement').mockReturnValue({ id: null });
      jest.spyOn(dataelementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataelement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataelement }));
      saveSubject.complete();

      // THEN
      expect(dataelementFormService.getDataelement).toHaveBeenCalled();
      expect(dataelementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataelement>>();
      const dataelement = { id: 'ABC' };
      jest.spyOn(dataelementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dataelementService.update).toHaveBeenCalled();
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

    describe('compareCategorycombo', () => {
      it('Should forward to categorycomboService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(categorycomboService, 'compareCategorycombo');
        comp.compareCategorycombo(entity, entity2);
        expect(categorycomboService.compareCategorycombo).toHaveBeenCalledWith(entity, entity2);
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
