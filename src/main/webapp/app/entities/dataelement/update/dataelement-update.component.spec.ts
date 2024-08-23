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
import { IDataset } from 'app/entities/dataset/dataset.model';
import { DatasetService } from 'app/entities/dataset/service/dataset.service';
import { IProgramStage } from 'app/entities/program-stage/program-stage.model';
import { ProgramStageService } from 'app/entities/program-stage/service/program-stage.service';
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
  let datasetService: DatasetService;
  let programStageService: ProgramStageService;

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
    datasetService = TestBed.inject(DatasetService);
    programStageService = TestBed.inject(ProgramStageService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Project query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 24602 };
      dataelement.project = project;

      const projectCollection: IProject[] = [{ id: 21128 }];
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
      const createdBy: IDHISUser = { id: '34881db4-3448-4db1-a608-b83e7d3bbb3a' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '3d0ece05-d15f-4a3d-a740-0710c0a7a43c' };
      dataelement.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '49e22701-46b2-46cf-a8f6-da5b6277b990' }];
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
      const categoryCombo: ICategorycombo = { id: '92a18f14-9f26-4f5f-8179-12dd03528549' };
      dataelement.categoryCombo = categoryCombo;

      const categorycomboCollection: ICategorycombo[] = [{ id: 'b9d49675-2eec-4069-be16-565403872432' }];
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
      const optionSet: IOptionset = { id: '70151ef1-f602-4cf4-8afc-548e23bf7b56' };
      dataelement.optionSet = optionSet;

      const optionsetCollection: IOptionset[] = [{ id: 'a2693341-a4ca-4e86-acd9-a69a70c83b59' }];
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

    it('Should call Dataset query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const datasets: IDataset[] = [{ id: 'b607c556-0d4a-47e0-9421-949f282b07cc' }];
      dataelement.datasets = datasets;

      const datasetCollection: IDataset[] = [{ id: 'ff17240e-7425-4d8f-892b-b185431165f9' }];
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

    it('Should call ProgramStage query and add missing value', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const programStages: IProgramStage[] = [{ id: 'e1d64e74-b17c-4e81-bc3e-4e514fcdad11' }];
      dataelement.programStages = programStages;

      const programStageCollection: IProgramStage[] = [{ id: '01b6cded-68c0-4a43-98fc-4ace2be908d6' }];
      jest.spyOn(programStageService, 'query').mockReturnValue(of(new HttpResponse({ body: programStageCollection })));
      const additionalProgramStages = [...programStages];
      const expectedCollection: IProgramStage[] = [...additionalProgramStages, ...programStageCollection];
      jest.spyOn(programStageService, 'addProgramStageToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(programStageService.query).toHaveBeenCalled();
      expect(programStageService.addProgramStageToCollectionIfMissing).toHaveBeenCalledWith(
        programStageCollection,
        ...additionalProgramStages.map(expect.objectContaining),
      );
      expect(comp.programStagesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dataelement: IDataelement = { id: 'CBA' };
      const project: IProject = { id: 19412 };
      dataelement.project = project;
      const createdBy: IDHISUser = { id: 'cf8ceb0b-f60a-400d-9157-43a191d5ab07' };
      dataelement.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'b3fb9280-ac3b-4df2-96ff-4b2eb7e90439' };
      dataelement.lastUpdatedBy = lastUpdatedBy;
      const categoryCombo: ICategorycombo = { id: '60c0e846-56bf-461b-a1a1-43e008b60ccc' };
      dataelement.categoryCombo = categoryCombo;
      const optionSet: IOptionset = { id: 'c5d3e47b-3ea0-4b4f-b585-95b80cf0217b' };
      dataelement.optionSet = optionSet;
      const dataset: IDataset = { id: '60cf7719-b8ec-4b31-ac75-039c96044df4' };
      dataelement.datasets = [dataset];
      const programStage: IProgramStage = { id: 'ea31a87e-852d-4353-a169-421af0f845c1' };
      dataelement.programStages = [programStage];

      activatedRoute.data = of({ dataelement });
      comp.ngOnInit();

      expect(comp.projectsSharedCollection).toContain(project);
      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.categorycombosSharedCollection).toContain(categoryCombo);
      expect(comp.optionsetsSharedCollection).toContain(optionSet);
      expect(comp.datasetsSharedCollection).toContain(dataset);
      expect(comp.programStagesSharedCollection).toContain(programStage);
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

    describe('compareDataset', () => {
      it('Should forward to datasetService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(datasetService, 'compareDataset');
        comp.compareDataset(entity, entity2);
        expect(datasetService.compareDataset).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProgramStage', () => {
      it('Should forward to programStageService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(programStageService, 'compareProgramStage');
        comp.compareProgramStage(entity, entity2);
        expect(programStageService.compareProgramStage).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
