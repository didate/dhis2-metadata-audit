import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../program-rule.test-samples';

import { ProgramRuleFormService } from './program-rule-form.service';

describe('ProgramRule Form Service', () => {
  let service: ProgramRuleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramRuleFormService);
  });

  describe('Service methods', () => {
    describe('createProgramRuleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramRuleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            name: expect.any(Object),
            displayName: expect.any(Object),
            priority: expect.any(Object),
            condition: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
          }),
        );
      });

      it('passing IProgramRule should create a new form with FormGroup', () => {
        const formGroup = service.createProgramRuleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            name: expect.any(Object),
            displayName: expect.any(Object),
            priority: expect.any(Object),
            condition: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
          }),
        );
      });
    });

    describe('getProgramRule', () => {
      it('should return NewProgramRule for default ProgramRule initial value', () => {
        const formGroup = service.createProgramRuleFormGroup(sampleWithNewData);

        const programRule = service.getProgramRule(formGroup) as any;

        expect(programRule).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgramRule for empty ProgramRule initial value', () => {
        const formGroup = service.createProgramRuleFormGroup();

        const programRule = service.getProgramRule(formGroup) as any;

        expect(programRule).toMatchObject({});
      });

      it('should return IProgramRule', () => {
        const formGroup = service.createProgramRuleFormGroup(sampleWithRequiredData);

        const programRule = service.getProgramRule(formGroup) as any;

        expect(programRule).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgramRule should not enable id FormControl', () => {
        const formGroup = service.createProgramRuleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgramRule should disable id FormControl', () => {
        const formGroup = service.createProgramRuleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
