import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../indicator.test-samples';

import { IndicatorFormService } from './indicator-form.service';

describe('Indicator Form Service', () => {
  let service: IndicatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndicatorFormService);
  });

  describe('Service methods', () => {
    describe('createIndicatorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIndicatorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            shortName: expect.any(Object),
            displayShortName: expect.any(Object),
            displayName: expect.any(Object),
            displayFormName: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            publicAccess: expect.any(Object),
            dimensionItemType: expect.any(Object),
            annualized: expect.any(Object),
            numerator: expect.any(Object),
            numeratorDescription: expect.any(Object),
            denominator: expect.any(Object),
            denominatorDescription: expect.any(Object),
            displayNumeratorDescription: expect.any(Object),
            displayDenominatorDescription: expect.any(Object),
            dimensionItem: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            indicatorType: expect.any(Object),
            datasets: expect.any(Object),
          }),
        );
      });

      it('passing IIndicator should create a new form with FormGroup', () => {
        const formGroup = service.createIndicatorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            shortName: expect.any(Object),
            displayShortName: expect.any(Object),
            displayName: expect.any(Object),
            displayFormName: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            publicAccess: expect.any(Object),
            dimensionItemType: expect.any(Object),
            annualized: expect.any(Object),
            numerator: expect.any(Object),
            numeratorDescription: expect.any(Object),
            denominator: expect.any(Object),
            denominatorDescription: expect.any(Object),
            displayNumeratorDescription: expect.any(Object),
            displayDenominatorDescription: expect.any(Object),
            dimensionItem: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            indicatorType: expect.any(Object),
            datasets: expect.any(Object),
          }),
        );
      });
    });

    describe('getIndicator', () => {
      it('should return NewIndicator for default Indicator initial value', () => {
        const formGroup = service.createIndicatorFormGroup(sampleWithNewData);

        const indicator = service.getIndicator(formGroup) as any;

        expect(indicator).toMatchObject(sampleWithNewData);
      });

      it('should return NewIndicator for empty Indicator initial value', () => {
        const formGroup = service.createIndicatorFormGroup();

        const indicator = service.getIndicator(formGroup) as any;

        expect(indicator).toMatchObject({});
      });

      it('should return IIndicator', () => {
        const formGroup = service.createIndicatorFormGroup(sampleWithRequiredData);

        const indicator = service.getIndicator(formGroup) as any;

        expect(indicator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIndicator should not enable id FormControl', () => {
        const formGroup = service.createIndicatorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIndicator should disable id FormControl', () => {
        const formGroup = service.createIndicatorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
