import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { DatasetService } from 'app/entities/dataset/service/dataset.service';
import { IOrganisationUnit } from '../organisation-unit.model';
import { OrganisationUnitService } from '../service/organisation-unit.service';
import { OrganisationUnitFormService } from './organisation-unit-form.service';

import { OrganisationUnitUpdateComponent } from './organisation-unit-update.component';

describe('OrganisationUnit Management Update Component', () => {
  let comp: OrganisationUnitUpdateComponent;
  let fixture: ComponentFixture<OrganisationUnitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let organisationUnitFormService: OrganisationUnitFormService;
  let organisationUnitService: OrganisationUnitService;
  let dHISUserService: DHISUserService;
  let programService: ProgramService;
  let datasetService: DatasetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [OrganisationUnitUpdateComponent],
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
      .overrideTemplate(OrganisationUnitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrganisationUnitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    organisationUnitFormService = TestBed.inject(OrganisationUnitFormService);
    organisationUnitService = TestBed.inject(OrganisationUnitService);
    dHISUserService = TestBed.inject(DHISUserService);
    programService = TestBed.inject(ProgramService);
    datasetService = TestBed.inject(DatasetService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DHISUser query and add missing value', () => {
      const organisationUnit: IOrganisationUnit = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '60caddfb-e284-4888-9813-c010d7798d5b' };
      organisationUnit.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '166cd1b5-1075-4880-8422-8c60d1dbb3cd' };
      organisationUnit.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: '683de805-c3a5-4a76-9b09-5ef733097c1c' }];
      jest.spyOn(dHISUserService, 'query').mockReturnValue(of(new HttpResponse({ body: dHISUserCollection })));
      const additionalDHISUsers = [createdBy, lastUpdatedBy];
      const expectedCollection: IDHISUser[] = [...additionalDHISUsers, ...dHISUserCollection];
      jest.spyOn(dHISUserService, 'addDHISUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      expect(dHISUserService.query).toHaveBeenCalled();
      expect(dHISUserService.addDHISUserToCollectionIfMissing).toHaveBeenCalledWith(
        dHISUserCollection,
        ...additionalDHISUsers.map(expect.objectContaining),
      );
      expect(comp.dHISUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Program query and add missing value', () => {
      const organisationUnit: IOrganisationUnit = { id: 'CBA' };
      const programs: IProgram[] = [{ id: 'e48769fe-a44e-40d8-a702-f4bf5b07c4d8' }];
      organisationUnit.programs = programs;

      const programCollection: IProgram[] = [{ id: '2f4b8b48-131d-49fd-b37e-f425d161b3c1' }];
      jest.spyOn(programService, 'query').mockReturnValue(of(new HttpResponse({ body: programCollection })));
      const additionalPrograms = [...programs];
      const expectedCollection: IProgram[] = [...additionalPrograms, ...programCollection];
      jest.spyOn(programService, 'addProgramToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      expect(programService.query).toHaveBeenCalled();
      expect(programService.addProgramToCollectionIfMissing).toHaveBeenCalledWith(
        programCollection,
        ...additionalPrograms.map(expect.objectContaining),
      );
      expect(comp.programsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dataset query and add missing value', () => {
      const organisationUnit: IOrganisationUnit = { id: 'CBA' };
      const datasets: IDataset[] = [{ id: 'c98059f5-2e18-4875-bae5-d115203008b7' }];
      organisationUnit.datasets = datasets;

      const datasetCollection: IDataset[] = [{ id: 'e59291c9-9454-4674-a4d5-0a9c2c2a91b6' }];
      jest.spyOn(datasetService, 'query').mockReturnValue(of(new HttpResponse({ body: datasetCollection })));
      const additionalDatasets = [...datasets];
      const expectedCollection: IDataset[] = [...additionalDatasets, ...datasetCollection];
      jest.spyOn(datasetService, 'addDatasetToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      expect(datasetService.query).toHaveBeenCalled();
      expect(datasetService.addDatasetToCollectionIfMissing).toHaveBeenCalledWith(
        datasetCollection,
        ...additionalDatasets.map(expect.objectContaining),
      );
      expect(comp.datasetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const organisationUnit: IOrganisationUnit = { id: 'CBA' };
      const createdBy: IDHISUser = { id: '7cf6b5c8-50f0-45a0-ac7b-fb22216348d3' };
      organisationUnit.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: '1f639dcb-bcdd-4dc7-af38-cac2ce7a327f' };
      organisationUnit.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '47968a92-0c34-4b6d-bfac-e7ca5155da35' };
      organisationUnit.programs = [program];
      const dataset: IDataset = { id: 'be20d7dd-bf5c-4f57-8115-03be7b9675c8' };
      organisationUnit.datasets = [dataset];

      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      expect(comp.dHISUsersSharedCollection).toContain(createdBy);
      expect(comp.dHISUsersSharedCollection).toContain(lastUpdatedBy);
      expect(comp.programsSharedCollection).toContain(program);
      expect(comp.datasetsSharedCollection).toContain(dataset);
      expect(comp.organisationUnit).toEqual(organisationUnit);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganisationUnit>>();
      const organisationUnit = { id: 'ABC' };
      jest.spyOn(organisationUnitFormService, 'getOrganisationUnit').mockReturnValue(organisationUnit);
      jest.spyOn(organisationUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organisationUnit }));
      saveSubject.complete();

      // THEN
      expect(organisationUnitFormService.getOrganisationUnit).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(organisationUnitService.update).toHaveBeenCalledWith(expect.objectContaining(organisationUnit));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganisationUnit>>();
      const organisationUnit = { id: 'ABC' };
      jest.spyOn(organisationUnitFormService, 'getOrganisationUnit').mockReturnValue({ id: null });
      jest.spyOn(organisationUnitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organisationUnit: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: organisationUnit }));
      saveSubject.complete();

      // THEN
      expect(organisationUnitFormService.getOrganisationUnit).toHaveBeenCalled();
      expect(organisationUnitService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrganisationUnit>>();
      const organisationUnit = { id: 'ABC' };
      jest.spyOn(organisationUnitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ organisationUnit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(organisationUnitService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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
