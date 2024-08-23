import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dataset.test-samples';

import { DatasetFormService } from './dataset-form.service';

describe('Dataset Form Service', () => {
  let service: DatasetFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DatasetFormService);
  });

  describe('Service methods', () => {
    describe('createDatasetFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDatasetFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            shortName: expect.any(Object),
            description: expect.any(Object),
            dimensionItemType: expect.any(Object),
            periodType: expect.any(Object),
            mobile: expect.any(Object),
            version: expect.any(Object),
            expiryDays: expect.any(Object),
            timelyDays: expect.any(Object),
            notifyCompletingUser: expect.any(Object),
            openFuturePeriods: expect.any(Object),
            openPeriodsAfterCoEndDate: expect.any(Object),
            fieldCombinationRequired: expect.any(Object),
            validCompleteOnly: expect.any(Object),
            noValueRequiresComment: expect.any(Object),
            skipOffline: expect.any(Object),
            dataElementDecoration: expect.any(Object),
            renderAsTabs: expect.any(Object),
            renderHorizontally: expect.any(Object),
            compulsoryFieldsCompleteOnly: expect.any(Object),
            formType: expect.any(Object),
            displayName: expect.any(Object),
            dimensionItem: expect.any(Object),
            displayShortName: expect.any(Object),
            displayDescription: expect.any(Object),
            displayFormName: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            categoryCombo: expect.any(Object),
            dataElements: expect.any(Object),
            organisationUnits: expect.any(Object),
          }),
        );
      });

      it('passing IDataset should create a new form with FormGroup', () => {
        const formGroup = service.createDatasetFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            shortName: expect.any(Object),
            description: expect.any(Object),
            dimensionItemType: expect.any(Object),
            periodType: expect.any(Object),
            mobile: expect.any(Object),
            version: expect.any(Object),
            expiryDays: expect.any(Object),
            timelyDays: expect.any(Object),
            notifyCompletingUser: expect.any(Object),
            openFuturePeriods: expect.any(Object),
            openPeriodsAfterCoEndDate: expect.any(Object),
            fieldCombinationRequired: expect.any(Object),
            validCompleteOnly: expect.any(Object),
            noValueRequiresComment: expect.any(Object),
            skipOffline: expect.any(Object),
            dataElementDecoration: expect.any(Object),
            renderAsTabs: expect.any(Object),
            renderHorizontally: expect.any(Object),
            compulsoryFieldsCompleteOnly: expect.any(Object),
            formType: expect.any(Object),
            displayName: expect.any(Object),
            dimensionItem: expect.any(Object),
            displayShortName: expect.any(Object),
            displayDescription: expect.any(Object),
            displayFormName: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            categoryCombo: expect.any(Object),
            dataElements: expect.any(Object),
            organisationUnits: expect.any(Object),
          }),
        );
      });
    });

    describe('getDataset', () => {
      it('should return NewDataset for default Dataset initial value', () => {
        const formGroup = service.createDatasetFormGroup(sampleWithNewData);

        const dataset = service.getDataset(formGroup) as any;

        expect(dataset).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataset for empty Dataset initial value', () => {
        const formGroup = service.createDatasetFormGroup();

        const dataset = service.getDataset(formGroup) as any;

        expect(dataset).toMatchObject({});
      });

      it('should return IDataset', () => {
        const formGroup = service.createDatasetFormGroup(sampleWithRequiredData);

        const dataset = service.getDataset(formGroup) as any;

        expect(dataset).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataset should not enable id FormControl', () => {
        const formGroup = service.createDatasetFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataset should disable id FormControl', () => {
        const formGroup = service.createDatasetFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
