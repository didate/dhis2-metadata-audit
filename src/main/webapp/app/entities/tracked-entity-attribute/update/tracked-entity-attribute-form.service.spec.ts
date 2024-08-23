import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tracked-entity-attribute.test-samples';

import { TrackedEntityAttributeFormService } from './tracked-entity-attribute-form.service';

describe('TrackedEntityAttribute Form Service', () => {
  let service: TrackedEntityAttributeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrackedEntityAttributeFormService);
  });

  describe('Service methods', () => {
    describe('createTrackedEntityAttributeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrackedEntityAttributeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            name: expect.any(Object),
            shortName: expect.any(Object),
            generated: expect.any(Object),
            valueType: expect.any(Object),
            confidential: expect.any(Object),
            displayFormName: expect.any(Object),
            uniquee: expect.any(Object),
            dimensionItemType: expect.any(Object),
            aggregationType: expect.any(Object),
            displayInListNoProgram: expect.any(Object),
            displayName: expect.any(Object),
            patterne: expect.any(Object),
            skipSynchronization: expect.any(Object),
            displayShortName: expect.any(Object),
            periodOffset: expect.any(Object),
            displayOnVisitSchedule: expect.any(Object),
            formName: expect.any(Object),
            orgunitScope: expect.any(Object),
            dimensionItem: expect.any(Object),
            inherit: expect.any(Object),
            optionSetValue: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            optionSet: expect.any(Object),
            programs: expect.any(Object),
          })
        );
      });

      it('passing ITrackedEntityAttribute should create a new form with FormGroup', () => {
        const formGroup = service.createTrackedEntityAttributeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastUpdated: expect.any(Object),
            created: expect.any(Object),
            name: expect.any(Object),
            shortName: expect.any(Object),
            generated: expect.any(Object),
            valueType: expect.any(Object),
            confidential: expect.any(Object),
            displayFormName: expect.any(Object),
            uniquee: expect.any(Object),
            dimensionItemType: expect.any(Object),
            aggregationType: expect.any(Object),
            displayInListNoProgram: expect.any(Object),
            displayName: expect.any(Object),
            patterne: expect.any(Object),
            skipSynchronization: expect.any(Object),
            displayShortName: expect.any(Object),
            periodOffset: expect.any(Object),
            displayOnVisitSchedule: expect.any(Object),
            formName: expect.any(Object),
            orgunitScope: expect.any(Object),
            dimensionItem: expect.any(Object),
            inherit: expect.any(Object),
            optionSetValue: expect.any(Object),
            track: expect.any(Object),
            project: expect.any(Object),
            createdBy: expect.any(Object),
            lastUpdatedBy: expect.any(Object),
            optionSet: expect.any(Object),
            programs: expect.any(Object),
          })
        );
      });
    });

    describe('getTrackedEntityAttribute', () => {
      it('should return NewTrackedEntityAttribute for default TrackedEntityAttribute initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrackedEntityAttributeFormGroup(sampleWithNewData);

        const trackedEntityAttribute = service.getTrackedEntityAttribute(formGroup) as any;

        expect(trackedEntityAttribute).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrackedEntityAttribute for empty TrackedEntityAttribute initial value', () => {
        const formGroup = service.createTrackedEntityAttributeFormGroup();

        const trackedEntityAttribute = service.getTrackedEntityAttribute(formGroup) as any;

        expect(trackedEntityAttribute).toMatchObject({});
      });

      it('should return ITrackedEntityAttribute', () => {
        const formGroup = service.createTrackedEntityAttributeFormGroup(sampleWithRequiredData);

        const trackedEntityAttribute = service.getTrackedEntityAttribute(formGroup) as any;

        expect(trackedEntityAttribute).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrackedEntityAttribute should not enable id FormControl', () => {
        const formGroup = service.createTrackedEntityAttributeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrackedEntityAttribute should disable id FormControl', () => {
        const formGroup = service.createTrackedEntityAttributeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
