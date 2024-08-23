import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../organisation-unit.test-samples';

import { OrganisationUnitFormService } from './organisation-unit-form.service';

describe('OrganisationUnit Form Service', () => {
  let service: OrganisationUnitFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrganisationUnitFormService);
  });

  describe('Service methods', () => {
    describe('createOrganisationUnitFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrganisationUnitFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            path: expect.any(Object),
            openingDate: expect.any(Object),
            level: expect.any(Object),
            track: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            programs: expect.any(Object),
            datasets: expect.any(Object),
          }),
        );
      });

      it('passing IOrganisationUnit should create a new form with FormGroup', () => {
        const formGroup = service.createOrganisationUnitFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            path: expect.any(Object),
            openingDate: expect.any(Object),
            level: expect.any(Object),
            track: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            programs: expect.any(Object),
            datasets: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrganisationUnit', () => {
      it('should return NewOrganisationUnit for default OrganisationUnit initial value', () => {
        const formGroup = service.createOrganisationUnitFormGroup(sampleWithNewData);

        const organisationUnit = service.getOrganisationUnit(formGroup) as any;

        expect(organisationUnit).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrganisationUnit for empty OrganisationUnit initial value', () => {
        const formGroup = service.createOrganisationUnitFormGroup();

        const organisationUnit = service.getOrganisationUnit(formGroup) as any;

        expect(organisationUnit).toMatchObject({});
      });

      it('should return IOrganisationUnit', () => {
        const formGroup = service.createOrganisationUnitFormGroup(sampleWithRequiredData);

        const organisationUnit = service.getOrganisationUnit(formGroup) as any;

        expect(organisationUnit).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrganisationUnit should not enable id FormControl', () => {
        const formGroup = service.createOrganisationUnitFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrganisationUnit should disable id FormControl', () => {
        const formGroup = service.createOrganisationUnitFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
