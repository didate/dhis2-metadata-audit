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
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { DatasetService } from 'app/entities/dataset/service/dataset.service';
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
  let programService: ProgramService;
  let datasetService: DatasetService;

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
    programService = TestBed.inject(ProgramService);
    datasetService = TestBed.inject(DatasetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 19615 };
      dataelement.project = project;

      const projectCollection: IProject[] = [{ id: 23001 }];
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
      const createdBy: IDHISUser = { id: '4aac75f0-ad80-4189-b959-39f1eed2aada' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '360a1f3d-7327-4081-a56a-50ebdf626d9b' };
      dataelement.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'a0b696e7-2ec3-4c3f-ac01-a9ef38696d8e' }];
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
      const categoryCombo: ICategorycombo = { id: 'f4d53f09-76af-46ad-a892-4f41cc085a75' };
      dataelement.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: '5653b340-35b0-4b7e-9469-da5da738b989' }];
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
      const optionSet: IOptionset = { id: 'aab21c9b-c1e9-443a-b727-cf74b2607002' };
      dataelement.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: '965d1b9a-7acc-47df-b314-909a212f4c5e' }];
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

    it('Should call Program query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const programs: IProgram[] = [{ id: '98cef7e4-515f-4aa1-b34a-380caf65a863' }];
      dataelement.programs = programs;

      const programCollection: IProgram[] = [{ id: 'd0603a4f-0a30-4c43-8283-7255dba70358' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [...programs];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataset query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const datasets: IDataset[] = [{ id: '683f0cee-9548-4208-8bbc-0bd5df017990' }];
      dataelement.datasets = datasets;

      const datasetCollection: IDataset[] = [{ id: '6ed5938a-ea1d-439c-b7c7-4dc57300d789' }];
      jest.spyOn(datasetService, 'query').mockReturnValue(of(new HttpResponse({ body: datasetCollection })));
      const additionalDatasets = [...datasets];
      const expectedCollection: IDataset[] = [...additionalDatasets, ...datasetCollection];
      jest.spyOn(datasetService, 'addDatasetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(datasetService.query).toHaveBeenCalled();
      expect(datasetService.addDatasetToCollectionIfMissing).toHaveBeenCalledWith(
        datasetCollection,
        ...additionalDatasets.map(expect.objectContaining),
      );
      expect(comp.datasetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 31616 };
      dataelement.project = project;
      const createdBy: IDHISUser = { id: '7cafbacf-f4ed-4046-a9fe-3d78f387a2df' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '3e642742-0929-44f7-9ba5-aecb9c562791' };
      dataelement.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: 'cc243c60-3bdf-4f73-ae1e-fcc77f9906b0' };
      dataelement.categoryCombo = categoryCombo;
      const optionSet: IOptionset = { id: '86c9baad-ca74-4101-b41f-e6c3ff95d335' };
      dataelement.optionSet = optionSet;
      const program: IProgram = { id: 'c0536fdf-3287-4e8e-b494-c372a45e23c4' };
      dataelement.programs = [program];
      const dataset: IDataset = { id: 'a44e57bc-3fcb-4671-89a2-bd880f26b22c' };
      dataelement.datasets = [dataset];

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.optionsetsSharedCollection).toContain(optionSet);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.datasetsSharedCollection).toContain(dataset);
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

    describe('compareProgram', () => {
      it('Should forward to programService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programService, 'compareProgram');
        comp.compareProgram(entity, entity2);
        expect(programService.compareProgram).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDataset', () => {
      it('Should forward to datasetService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(datasetService, 'compareDataset');
        comp.compareDataset(entity, entity2);
        expect(datasetService.compareDataset).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
