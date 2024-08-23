import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../program.test-samples';

import { ProgramFormService } from './program-form.service';

describe('Program Form Service', () => {
  let service: ProgramFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramFormService);
  });

  describe('Service methods', () => {
    describe('createProgramFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProgramFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            shortName: expect.any(Object),
            description: expect.any(Object),
            version: expect.any(Object),
            enrollmentDateLabel: expect.any(Object),
            incidentDateLabel: expect.any(Object),
            programType: expect.any(Object),
            displayIncidentDate: expect.any(Object),
            ignoreOverdueEvents: expect.any(Object),
            userRoles: expect.any(Object),
            programIndicators: expect.any(Object),
            programRuleVariables: expect.any(Object),
            onlyEnrollOnce: expect.any(Object),
            notificationTemplates: expect.any(Object),
            selectEnrollmentDatesInFuture: expect.any(Object),
            selectIncidentDatesInFuture: expect.any(Object),
            trackedEntityType: expect.any(Object),
            style: expect.any(Object),
            skipOffline: expect.any(Object),
            displayFrontPageList: expect.any(Object),
            useFirstStageDuringRegistration: expect.any(Object),
            expiryDays: expect.any(Object),
            completeEventsExpiryDays: expect.any(Object),
            openDaysAfterCoEndDate: expect.any(Object),
            minAttributesRequiredToSearch: expect.any(Object),
            maxTeiCountToReturn: expect.any(Object),
            accessLevel: expect.any(Object),
            displayEnrollmentDateLabel: expect.any(Object),
            displayIncidentDateLabel: expect.any(Object),
            registration: expect.any(Object),
            withoutRegistration: expect.any(Object),
            displayShortName: expect.any(Object),
            displayDescription: expect.any(Object),
            displayFormName: expect.any(Object),
            displayName: expect.any(Object),
            attributeValuesCount: expect.any(Object),
            organisationUnitsCount: expect.any(Object),
            programStagesCount: expect.any(Object),
            programSectionsCount: expect.any(Object),
            programTrackedEntityAttributesCount: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            categoryCombo: expect.any(Object),
            dataElements: expect.any(Object),
            organisationUnits: expect.any(Object),
          }),
        );
      });

      it('passing IProgram should create a new form with FormGroup', () => {
        const formGroup = service.createProgramFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            created: expect.any(Object),
            lastUpdated: expect.any(Object),
            shortName: expect.any(Object),
            description: expect.any(Object),
            version: expect.any(Object),
            enrollmentDateLabel: expect.any(Object),
            incidentDateLabel: expect.any(Object),
            programType: expect.any(Object),
            displayIncidentDate: expect.any(Object),
            ignoreOverdueEvents: expect.any(Object),
            userRoles: expect.any(Object),
            programIndicators: expect.any(Object),
            programRuleVariables: expect.any(Object),
            onlyEnrollOnce: expect.any(Object),
            notificationTemplates: expect.any(Object),
            selectEnrollmentDatesInFuture: expect.any(Object),
            selectIncidentDatesInFuture: expect.any(Object),
            trackedEntityType: expect.any(Object),
            style: expect.any(Object),
            skipOffline: expect.any(Object),
            displayFrontPageList: expect.any(Object),
            useFirstStageDuringRegistration: expect.any(Object),
            expiryDays: expect.any(Object),
            completeEventsExpiryDays: expect.any(Object),
            openDaysAfterCoEndDate: expect.any(Object),
            minAttributesRequiredToSearch: expect.any(Object),
            maxTeiCountToReturn: expect.any(Object),
            accessLevel: expect.any(Object),
            displayEnrollmentDateLabel: expect.any(Object),
            displayIncidentDateLabel: expect.any(Object),
            registration: expect.any(Object),
            withoutRegistration: expect.any(Object),
            displayShortName: expect.any(Object),
            displayDescription: expect.any(Object),
            displayFormName: expect.any(Object),
            displayName: expect.any(Object),
            attributeValuesCount: expect.any(Object),
            organisationUnitsCount: expect.any(Object),
            programStagesCount: expect.any(Object),
            programSectionsCount: expect.any(Object),
            programTrackedEntityAttributesCount: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            categoryCombo: expect.any(Object),
            dataElements: expect.any(Object),
            organisationUnits: expect.any(Object),
          }),
        );
      });
    });

    describe('getProgram', () => {
      it('should return NewProgram for default Program initial value', () => {
        const formGroup = service.createProgramFormGroup(sampleWithNewData);

        const program = service.getProgram(formGroup) as any;

        expect(program).toMatchObject(sampleWithNewData);
      });

      it('should return NewProgram for empty Program initial value', () => {
        const formGroup = service.createProgramFormGroup();

        const program = service.getProgram(formGroup) as any;

        expect(program).toMatchObject({});
      });

      it('should return IProgram', () => {
        const formGroup = service.createProgramFormGroup(sampleWithRequiredData);

        const program = service.getProgram(formGroup) as any;

        expect(program).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProgram should not enable id FormControl', () => {
        const formGroup = service.createProgramFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProgram should disable id FormControl', () => {
        const formGroup = service.createProgramFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
