import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../person-notifier.test-samples';

import { PersonNotifierFormService } from './person-notifier-form.service';

describe('PersonNotifier Form Service', () => {
  let service: PersonNotifierFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonNotifierFormService);
  });

  describe('Service methods', () => {
    describe('createPersonNotifierFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPersonNotifierFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            personName: expect.any(Object),
            personPhone: expect.any(Object),
            personEmail: expect.any(Object),
            personOrganization: expect.any(Object),
            project: expect.any(Object),
          })
        );
      });

      it('passing IPersonNotifier should create a new form with FormGroup', () => {
        const formGroup = service.createPersonNotifierFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            personName: expect.any(Object),
            personPhone: expect.any(Object),
            personEmail: expect.any(Object),
            personOrganization: expect.any(Object),
            project: expect.any(Object),
          })
        );
      });
    });

    describe('getPersonNotifier', () => {
      it('should return NewPersonNotifier for default PersonNotifier initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPersonNotifierFormGroup(sampleWithNewData);

        const personNotifier = service.getPersonNotifier(formGroup) as any;

        expect(personNotifier).toMatchObject(sampleWithNewData);
      });

      it('should return NewPersonNotifier for empty PersonNotifier initial value', () => {
        const formGroup = service.createPersonNotifierFormGroup();

        const personNotifier = service.getPersonNotifier(formGroup) as any;

        expect(personNotifier).toMatchObject({});
      });

      it('should return IPersonNotifier', () => {
        const formGroup = service.createPersonNotifierFormGroup(sampleWithRequiredData);

        const personNotifier = service.getPersonNotifier(formGroup) as any;

        expect(personNotifier).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPersonNotifier should not enable id FormControl', () => {
        const formGroup = service.createPersonNotifierFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPersonNotifier should disable id FormControl', () => {
        const formGroup = service.createPersonNotifierFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
