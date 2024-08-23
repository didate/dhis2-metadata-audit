import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../program-rule-action.test-samples';

import { ProgramRuleActionFormService } from './program-rule-action-form.service';

describe('ProgramRuleAction Form Service', () => {
  let service: ProgramRuleActionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramRuleActionFormService);
  });

  describe('Service methods', () => {
    describe('createProgramRuleActionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramRuleActionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            programRuleActionType: expect.any(Object),
            evaluationTime: expect.any(Object),
            data: expect.any(Object),
            templateUid: expect.any(Object),
            content: expect.any(Object),
            displayContent: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            programRule: expect.any(Object),
            trackedEntityAttribute: expect.any(Object),
            dataElement: expect.any(Object),
            optionGroup: expect.any(Object),
          }),
        );
      });

      it('passing IProgramRuleAction should create a new form with FormGroup', () => {
        const formGroup = service.createProgramRuleActionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            programRuleActionType: expect.any(Object),
            evaluationTime: expect.any(Object),
            data: expect.any(Object),
            templateUid: expect.any(Object),
            content: expect.any(Object),
            displayContent: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            programRule: expect.any(Object),
            trackedEntityAttribute: expect.any(Object),
            dataElement: expect.any(Object),
            optionGroup: expect.any(Object),
          }),
        );
      });
    });

    describe('getProgramRuleAction', () => {
      it('should return NewProgramRuleAction for default ProgramRuleAction initial value', () => {
        const formGroup = service.createProgramRuleActionFormGroup(sampleWithNewData);

        const programRuleAction = service.getProgramRuleAction(formGroup) as any;

        expect(programRuleAction).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgramRuleAction for empty ProgramRuleAction initial value', () => {
        const formGroup = service.createProgramRuleActionFormGroup();

        const programRuleAction = service.getProgramRuleAction(formGroup) as any;

        expect(programRuleAction).toMatchObject({});
      });

      it('should return IProgramRuleAction', () => {
        const formGroup = service.createProgramRuleActionFormGroup(sampleWithRequiredData);

        const programRuleAction = service.getProgramRuleAction(formGroup) as any;

        expect(programRuleAction).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgramRuleAction should not enable id FormControl', () => {
        const formGroup = service.createProgramRuleActionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgramRuleAction should disable id FormControl', () => {
        const formGroup = service.createProgramRuleActionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
