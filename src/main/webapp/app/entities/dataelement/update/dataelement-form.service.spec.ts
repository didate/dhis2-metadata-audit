import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dataelement.test-samples';

import { DataelementFormService } from './dataelement-form.service';

describe('Dataelement Form Service', () => {
  let service: DataelementFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataelementFormService);
  });

  describe('Service methods', () => {
    describe('createDataelementFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDataelementFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            shortName: expect.any(Object),
            formName: expect.any(Object),
            description: expect.any(Object),
            displayShortName: expect.any(Object),
            displayName: expect.any(Object),
            displayFormName: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            publicAccess: expect.any(Object),
            dimensionItemType: expect.any(Object),
            aggregationType: expect.any(Object),
            valueType: expect.any(Object),
            domainType: expect.any(Object),
            zeroIsSignificant: expect.any(Object),
            optionSetValue: expect.any(Object),
            dimensionItem: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            categoryCombo: expect.any(Object),
            optionSet: expect.any(Object),
            programs: expect.any(Object),
            datasets: expect.any(Object),
          }),
        );
      });

      it('passing IDataelement should create a new form with FormGroup', () => {
        const formGroup = service.createDataelementFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            shortName: expect.any(Object),
            formName: expect.any(Object),
            description: expect.any(Object),
            displayShortName: expect.any(Object),
            displayName: expect.any(Object),
            displayFormName: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            publicAccess: expect.any(Object),
            dimensionItemType: expect.any(Object),
            aggregationType: expect.any(Object),
            valueType: expect.any(Object),
            domainType: expect.any(Object),
            zeroIsSignificant: expect.any(Object),
            optionSetValue: expect.any(Object),
            dimensionItem: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            categoryCombo: expect.any(Object),
            optionSet: expect.any(Object),
            programs: expect.any(Object),
            datasets: expect.any(Object),
          }),
        );
      });
    });

    describe('getDataelement', () => {
      it('should return NewDataelement for default Dataelement initial value', () => {
        const formGroup = service.createDataelementFormGroup(sampleWithNewData);

        const dataelement = service.getDataelement(formGroup) as any;

        expect(dataelement).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataelement for empty Dataelement initial value', () => {
        const formGroup = service.createDataelementFormGroup();

        const dataelement = service.getDataelement(formGroup) as any;

        expect(dataelement).toMatchObject({});
      });

      it('should return IDataelement', () => {
        const formGroup = service.createDataelementFormGroup(sampleWithRequiredData);

        const dataelement = service.getDataelement(formGroup) as any;

        expect(dataelement).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataelement should not enable id FormControl', () => {
        const formGroup = service.createDataelementFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataelement should disable id FormControl', () => {
        const formGroup = service.createDataelementFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
