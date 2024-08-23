import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DataelementFormService } from './dataelement-form.service';
import { DataelementService } from '../service/dataelement.service';
import { IDataelement } from '../dataelement.model';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { CategorycomboService } from 'app/entities/categorycombo/service/categorycombo.service';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { OptionsetService } from 'app/entities/optionset/service/optionset.service';

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
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DataelementUpdateComponent],
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
      const project: IProject = { id: 88150 };
      dataelement.project = project;

      const projectCollection: IProject[] = [{ id: 8926 }];
      jest.spyOn(projectService, 'query').mockReturnValue(of(new HttpResponse({ body: projectCollection })));
      const additionalProjects = [project];
      const expectedCollection: IProject[] = [...additionalProjects, ...projectCollection];
      jest.spyOn(projectService, 'addProjectToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(projectService.query).toHaveBeenCalled();
      expect(projectService.addProjectToCollectionIfMissing).toHaveBeenCalledWith(
        projectCollection,
        ...additionalProjects.map(expect.objectContaining)
      );
      expect(comp.projectsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DHISUser query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '2f560acf-7540-483d-b11e-011d061b3150' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'd44c7afa-8f2d-41a5-9c71-d1eb3b9b9b0b' };
      dataelement.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '0c0afef3-ec72-485f-b540-d5bcb3cae3a5' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining)
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Categorycombo query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const categoryCombo: ICategorycombo = { id: '783dfc60-6e4c-4e0e-8259-522dd66bfe0e' };
      dataelement.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: '8a59f957-6a60-4d8f-9c02-c32c64976a71' }];
      jest.spyOn(categorycomboService, 'query').mockReturnValue(of(new HttpResponse({ body: categorycomboCollection })));
      const additionalCategorycombos = [categoryCombo];
      const expectedCollection: ICategorycombo[] = [...additionalCategorycombos, ...categorycomboCollection];
      jest.spyOn(categorycomboService, 'addCategorycomboToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(categorycomboService.query).toHaveBeenCalled();
      expect(categorycomboService.addCategorycomboToCollectionIfMissing).toHaveBeenCalledWith(
        categorycomboCollection,
        ...additionalCategorycombos.map(expect.objectContaining)
      );
      expect(comp.categorycombosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Optionset query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const optionSet: IOptionset = { id: 'a418c6bb-1a54-4b96-88c8-4c9fe85ead03' };
      dataelement.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: '4f096a77-7850-49eb-8401-ff11b19f4d38' }];
      jest.spyOn(optionsetService, 'query').mockReturnValue(of(new HttpResponse({ body: optionsetCollection })));
      const additionalOptionsets = [optionSet];
      const expectedCollection: IOptionset[] = [...additionalOptionsets, ...optionsetCollection];
      jest.spyOn(optionsetService, 'addOptionsetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(optionsetService.query).toHaveBeenCalled();
      expect(optionsetService.addOptionsetToCollectionIfMissing).toHaveBeenCalledWith(
        optionsetCollection,
        ...additionalOptionsets.map(expect.objectContaining)
      );
      expect(comp.optionsetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 2711 };
      dataelement.project = project;
      const createdBy: IDHISUser = { id: '89c518ed-dff9-4612-a8dd-e85b3af2c9ae' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'd4c5ba72-71e1-4737-ab71-f8a1a71aaec6' };
      dataelement.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '20ac43a3-98f4-4a7e-a0e3-86a890c46ab8' };
      dataelement.categoryCombo = categoryCombo;
      const optionSet: IOptionset = { id: '79fef54c-18f6-45b1-9e5a-d21edd2564bf' };
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
