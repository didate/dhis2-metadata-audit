import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dhis-user.test-samples';

import { DHISUserFormService } from './dhis-user-form.service';

describe('DHISUser Form Service', () => {
  let service: DHISUserFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DHISUserFormService);
  });

  describe('Service methods', () => {
    describe('createDHISUserFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDHISUserFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            displayName: expect.any(Object),
            username: expect.any(Object),
          }),
        );
      });

      it('passing IDHISUser should create a new form with FormGroup', () => {
        const formGroup = service.createDHISUserFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            displayName: expect.any(Object),
            username: expect.any(Object),
          }),
        );
      });
    });

    describe('getDHISUser', () => {
      it('should return NewDHISUser for default DHISUser initial value', () => {
        const formGroup = service.createDHISUserFormGroup(sampleWithNewData);

        const dHISUser = service.getDHISUser(formGroup) as any;

        expect(dHISUser).toMatchObject(sampleWithNewData);
      });

      it('should return NewDHISUser for empty DHISUser initial value', () => {
        const formGroup = service.createDHISUserFormGroup();

        const dHISUser = service.getDHISUser(formGroup) as any;

        expect(dHISUser).toMatchObject({});
      });

      it('should return IDHISUser', () => {
        const formGroup = service.createDHISUserFormGroup(sampleWithRequiredData);

        const dHISUser = service.getDHISUser(formGroup) as any;

        expect(dHISUser).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDHISUser should not enable id FormControl', () => {
        const formGroup = service.createDHISUserFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDHISUser should disable id FormControl', () => {
        const formGroup = service.createDHISUserFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
