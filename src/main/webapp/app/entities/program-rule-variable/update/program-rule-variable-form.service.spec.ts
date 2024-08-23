import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../program-rule-variable.test-samples';

import { ProgramRuleVariableFormService } from './program-rule-variable-form.service';

describe('ProgramRuleVariable Form Service', () => {
  let service: ProgramRuleVariableFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramRuleVariableFormService);
  });

  describe('Service methods', () => {
    describe('createProgramRuleVariableFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramRuleVariableFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            name: expect.any(Object),
            displayName: expect.any(Object),
            programRuleVariableSourceType: expect.any(Object),
            useCodeForOptionSet: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
            trackedEntityAttribute: expect.any(Object),
            dataElement: expect.any(Object),
          }),
        );
      });

      it('passing IProgramRuleVariable should create a new form with FormGroup', () => {
        const formGroup = service.createProgramRuleVariableFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            name: expect.any(Object),
            displayName: expect.any(Object),
            programRuleVariableSourceType: expect.any(Object),
            useCodeForOptionSet: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
            trackedEntityAttribute: expect.any(Object),
            dataElement: expect.any(Object),
          }),
        );
      });
    });

    describe('getProgramRuleVariable', () => {
      it('should return NewProgramRuleVariable for default ProgramRuleVariable initial value', () => {
        const formGroup = service.createProgramRuleVariableFormGroup(sampleWithNewData);

        const programRuleVariable = service.getProgramRuleVariable(formGroup) as any;

        expect(programRuleVariable).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgramRuleVariable for empty ProgramRuleVariable initial value', () => {
        const formGroup = service.createProgramRuleVariableFormGroup();

        const programRuleVariable = service.getProgramRuleVariable(formGroup) as any;

        expect(programRuleVariable).toMatchObject({});
      });

      it('should return IProgramRuleVariable', () => {
        const formGroup = service.createProgramRuleVariableFormGroup(sampleWithRequiredData);

        const programRuleVariable = service.getProgramRuleVariable(formGroup) as any;

        expect(programRuleVariable).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgramRuleVariable should not enable id FormControl', () => {
        const formGroup = service.createProgramRuleVariableFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgramRuleVariable should disable id FormControl', () => {
        const formGroup = service.createProgramRuleVariableFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
