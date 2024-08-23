import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../option-group.test-samples';

import { OptionGroupFormService } from './option-group-form.service';

describe('OptionGroup Form Service', () => {
  let service: OptionGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OptionGroupFormService);
  });

  describe('Service methods', () => {
    describe('createOptionGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOptionGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
          }),
        );
      });

      it('passing IOptionGroup should create a new form with FormGroup', () => {
        const formGroup = service.createOptionGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
          }),
        );
      });
    });

    describe('getOptionGroup', () => {
      it('should return NewOptionGroup for default OptionGroup initial value', () => {
        const formGroup = service.createOptionGroupFormGroup(sampleWithNewData);

        const optionGroup = service.getOptionGroup(formGroup) as any;

        expect(optionGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewOptionGroup for empty OptionGroup initial value', () => {
        const formGroup = service.createOptionGroupFormGroup();

        const optionGroup = service.getOptionGroup(formGroup) as any;

        expect(optionGroup).toMatchObject({});
      });

      it('should return IOptionGroup', () => {
        const formGroup = service.createOptionGroupFormGroup(sampleWithRequiredData);

        const optionGroup = service.getOptionGroup(formGroup) as any;

        expect(optionGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOptionGroup should not enable id FormControl', () => {
        const formGroup = service.createOptionGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOptionGroup should disable id FormControl', () => {
        const formGroup = service.createOptionGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
