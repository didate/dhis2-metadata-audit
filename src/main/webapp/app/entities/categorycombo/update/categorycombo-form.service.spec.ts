import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categorycombo.test-samples';

import { CategorycomboFormService } from './categorycombo-form.service';

describe('Categorycombo Form Service', () => {
  let service: CategorycomboFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategorycomboFormService);
  });

  describe('Service methods', () => {
    describe('createCategorycomboFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategorycomboFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing ICategorycombo should create a new form with FormGroup', () => {
        const formGroup = service.createCategorycomboFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getCategorycombo', () => {
      it('should return NewCategorycombo for default Categorycombo initial value', () => {
        const formGroup = service.createCategorycomboFormGroup(sampleWithNewData);

        const categorycombo = service.getCategorycombo(formGroup) as any;

        expect(categorycombo).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorycombo for empty Categorycombo initial value', () => {
        const formGroup = service.createCategorycomboFormGroup();

        const categorycombo = service.getCategorycombo(formGroup) as any;

        expect(categorycombo).toMatchObject({});
      });

      it('should return ICategorycombo', () => {
        const formGroup = service.createCategorycomboFormGroup(sampleWithRequiredData);

        const categorycombo = service.getCategorycombo(formGroup) as any;

        expect(categorycombo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorycombo should not enable id FormControl', () => {
        const formGroup = service.createCategorycomboFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategorycombo should disable id FormControl', () => {
        const formGroup = service.createCategorycomboFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
