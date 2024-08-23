import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../indicatortype.test-samples';

import { IndicatortypeFormService } from './indicatortype-form.service';

describe('Indicatortype Form Service', () => {
  let service: IndicatortypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndicatortypeFormService);
  });

  describe('Service methods', () => {
    describe('createIndicatortypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIndicatortypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });

      it('passing IIndicatortype should create a new form with FormGroup', () => {
        const formGroup = service.createIndicatortypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          })
        );
      });
    });

    describe('getIndicatortype', () => {
      it('should return NewIndicatortype for default Indicatortype initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIndicatortypeFormGroup(sampleWithNewData);

        const indicatortype = service.getIndicatortype(formGroup) as any;

        expect(indicatortype).toMatchObject(sampleWithNewData);
      });

      it('should return NewIndicatortype for empty Indicatortype initial value', () => {
        const formGroup = service.createIndicatortypeFormGroup();

        const indicatortype = service.getIndicatortype(formGroup) as any;

        expect(indicatortype).toMatchObject({});
      });

      it('should return IIndicatortype', () => {
        const formGroup = service.createIndicatortypeFormGroup(sampleWithRequiredData);

        const indicatortype = service.getIndicatortype(formGroup) as any;

        expect(indicatortype).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIndicatortype should not enable id FormControl', () => {
        const formGroup = service.createIndicatortypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIndicatortype should disable id FormControl', () => {
        const formGroup = service.createIndicatortypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
