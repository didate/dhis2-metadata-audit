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
      const createdBy: IDHISUser = { id: '8869f5f4-b11e-4411-8fe4-f6c37df5ae16' };
      organisationUnit.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'c2e40186-8f85-4fd0-ab74-085f4ff58f8a' };
      organisationUnit.lastUpdatedBy = lastUpdatedBy;

      const dHISUserCollection: IDHISUser[] = [{ id: 'e403f5a6-f54e-4430-883c-cbc68edbe404' }];
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
      const programs: IProgram[] = [{ id: '88c4a3cc-203b-414e-bc29-6a978b158e34' }];
      organisationUnit.programs = programs;

      const programCollection: IProgram[] = [{ id: '2232b8e0-d927-48f2-973b-954202165fce' }];
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
      const datasets: IDataset[] = [{ id: '330626f5-361c-42bb-9170-c34a52e76329' }];
      organisationUnit.datasets = datasets;

      const datasetCollection: IDataset[] = [{ id: '5fca022c-19e5-4622-b01b-4d810faa5a6c' }];
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
      const createdBy: IDHISUser = { id: '16b6a8af-36b8-44b9-a34a-89aa67e95121' };
      organisationUnit.createdBy = createdBy;
      const lastUpdatedBy: IDHISUser = { id: 'a50e91e8-8243-431e-ba42-b6ba2482bf83' };
      organisationUnit.lastUpdatedBy = lastUpdatedBy;
      const program: IProgram = { id: '2524678d-2133-41b6-a9a7-fb06474efa5a' };
      organisationUnit.programs = [program];
      const dataset: IDataset = { id: '4a920419-ec08-4366-a6ed-be80eeebffb8' };
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
