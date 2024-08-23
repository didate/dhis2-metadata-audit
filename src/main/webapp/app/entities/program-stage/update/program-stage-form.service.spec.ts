import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../program-stage.test-samples';

import { ProgramStageFormService } from './program-stage-form.service';

describe('ProgramStage Form Service', () => {
  let service: ProgramStageFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramStageFormService);
  });

  describe('Service methods', () => {
    describe('createProgramStageFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramStageFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            minDaysFromStart: expect.any(Object),
            executionDateLabel: expect.any(Object),
            autoGenerateEvent: expect.any(Object),
            validationStrategy: expect.any(Object),
            displayGenerateEventBox: expect.any(Object),
            featureType: expect.any(Object),
            blockEntryForm: expect.any(Object),
            preGenerateUID: expect.any(Object),
            remindCompleted: expect.any(Object),
            generatedByEnrollmentDate: expect.any(Object),
            allowGenerateNextVisit: expect.any(Object),
            openAfterEnrollment: expect.any(Object),
            sortOrder: expect.any(Object),
            hideDueDate: expect.any(Object),
            enableUserAssignment: expect.any(Object),
            referral: expect.any(Object),
            displayExecutionDateLabel: expect.any(Object),
            formType: expect.any(Object),
            displayFormName: expect.any(Object),
            displayName: expect.any(Object),
            repeatable: expect.any(Object),
            programStageDataElementsCount: expect.any(Object),
            programStageDataElementsContent: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
            programStageDataElements: expect.any(Object),
            programs: expect.any(Object),
          }),
        );
      });

      it('passing IProgramStage should create a new form with FormGroup', () => {
        const formGroup = service.createProgramStageFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            minDaysFromStart: expect.any(Object),
            executionDateLabel: expect.any(Object),
            autoGenerateEvent: expect.any(Object),
            validationStrategy: expect.any(Object),
            displayGenerateEventBox: expect.any(Object),
            featureType: expect.any(Object),
            blockEntryForm: expect.any(Object),
            preGenerateUID: expect.any(Object),
            remindCompleted: expect.any(Object),
            generatedByEnrollmentDate: expect.any(Object),
            allowGenerateNextVisit: expect.any(Object),
            openAfterEnrollment: expect.any(Object),
            sortOrder: expect.any(Object),
            hideDueDate: expect.any(Object),
            enableUserAssignment: expect.any(Object),
            referral: expect.any(Object),
            displayExecutionDateLabel: expect.any(Object),
            formType: expect.any(Object),
            displayFormName: expect.any(Object),
            displayName: expect.any(Object),
            repeatable: expect.any(Object),
            programStageDataElementsCount: expect.any(Object),
            programStageDataElementsContent: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            program: expect.any(Object),
            programStageDataElements: expect.any(Object),
            programs: expect.any(Object),
          }),
        );
      });
    });

    describe('getProgramStage', () => {
      it('should return NewProgramStage for default ProgramStage initial value', () => {
        const formGroup = service.createProgramStageFormGroup(sampleWithNewData);

        const programStage = service.getProgramStage(formGroup) as any;

        expect(programStage).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgramStage for empty ProgramStage initial value', () => {
        const formGroup = service.createProgramStageFormGroup();

        const programStage = service.getProgramStage(formGroup) as any;

        expect(programStage).toMatchObject({});
      });

      it('should return IProgramStage', () => {
        const formGroup = service.createProgramStageFormGroup(sampleWithRequiredData);

        const programStage = service.getProgramStage(formGroup) as any;

        expect(programStage).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgramStage should not enable id FormControl', () => {
        const formGroup = service.createProgramStageFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgramStage should disable id FormControl', () => {
        const formGroup = service.createProgramStageFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
