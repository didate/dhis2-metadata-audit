import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../program-indicator.test-samples';

import { ProgramIndicatorFormService } from './program-indicator-form.service';

describe('ProgramIndicator Form Service', () => {
  let service: ProgramIndicatorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramIndicatorFormService);
  });

  describe('Service methods', () => {
    describe('createProgramIndicatorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramIndicatorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            shortName: expect.any(Object),
            dimensionItemType: expect.any(Object),
            expression: expect.any(Object),
            filter: expect.any(Object),
            analyticsType: expect.any(Object),
            dimensionItem: expect.any(Object),
            displayShortName: expect.any(Object),
            displayName: expect.any(Object),
            displayFormName: expect.any(Object),
            track: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
            programs: expect.any(Object),
          })
        );
      });

      it('passing IProgramIndicator should create a new form with FormGroup', () => {
        const formGroup = service.createProgramIndicatorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            shortName: expect.any(Object),
            dimensionItemType: expect.any(Object),
            expression: expect.any(Object),
            filter: expect.any(Object),
            analyticsType: expect.any(Object),
            dimensionItem: expect.any(Object),
            displayShortName: expect.any(Object),
            displayName: expect.any(Object),
            displayFormName: expect.any(Object),
            track: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
            programs: expect.any(Object),
          })
        );
      });
    });

    describe('getProgramIndicator', () => {
      it('should return NewProgramIndicator for default ProgramIndicator initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProgramIndicatorFormGroup(sampleWithNewData);

        const programIndicator = service.getProgramIndicator(formGroup) as any;

        expect(programIndicator).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgramIndicator for empty ProgramIndicator initial value', () => {
        const formGroup = service.createProgramIndicatorFormGroup();

        const programIndicator = service.getProgramIndicator(formGroup) as any;

        expect(programIndicator).toMatchObject({});
      });

      it('should return IProgramIndicator', () => {
        const formGroup = service.createProgramIndicatorFormGroup(sampleWithRequiredData);

        const programIndicator = service.getProgramIndicator(formGroup) as any;

        expect(programIndicator).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgramIndicator should not enable id FormControl', () => {
        const formGroup = service.createProgramIndicatorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgramIndicator should disable id FormControl', () => {
        const formGroup = service.createProgramIndicatorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
