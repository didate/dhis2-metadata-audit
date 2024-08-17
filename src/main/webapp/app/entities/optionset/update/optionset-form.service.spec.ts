import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../optionset.test-samples';

import { OptionsetFormService } from './optionset-form.service';

describe('Optionset Form Service', () => {
  let service: OptionsetFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OptionsetFormService);
  });

  describe('Service methods', () => {
    describe('createOptionsetFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOptionsetFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IOptionset should create a new form with FormGroup', () => {
        const formGroup = service.createOptionsetFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getOptionset', () => {
      it('should return NewOptionset for default Optionset initial value', () => {
        const formGroup = service.createOptionsetFormGroup(sampleWithNewData);

        const optionset = service.getOptionset(formGroup) as any;

        expect(optionset).toMatchObject(sampleWithNewData);
      });

      it('should return NewOptionset for empty Optionset initial value', () => {
        const formGroup = service.createOptionsetFormGroup();

        const optionset = service.getOptionset(formGroup) as any;

        expect(optionset).toMatchObject({});
      });

      it('should return IOptionset', () => {
        const formGroup = service.createOptionsetFormGroup(sampleWithRequiredData);

        const optionset = service.getOptionset(formGroup) as any;

        expect(optionset).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOptionset should not enable id FormControl', () => {
        const formGroup = service.createOptionsetFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOptionset should disable id FormControl', () => {
        const formGroup = service.createOptionsetFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
