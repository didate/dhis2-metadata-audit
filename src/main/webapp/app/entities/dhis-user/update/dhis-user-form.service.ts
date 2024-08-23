import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDHISUser, NewDHISUser } from '../dhis-user.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDHISUser for edit and NewDHISUserFormGroupInput for create.
 */
type DHISUserFormGroupInput = IDHISUser | PartialWithRequiredKeyOf<NewDHISUser>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDHISUser | NewDHISUser> = Omit<T, 'lastLogin' | 'passwordLastUpdated' | 'created' | 'lastUpdated'> & {
  lastLogin?: string | null;
  passwordLastUpdated?: string | null;
  created?: string | null;
  lastUpdated?: string | null;
};

type DHISUserFormRawValue = FormValueOf<IDHISUser>;

type NewDHISUserFormRawValue = FormValueOf<NewDHISUser>;

type DHISUserFormDefaults = Pick<NewDHISUser, 'id' | 'lastLogin' | 'disabled' | 'passwordLastUpdated' | 'created' | 'lastUpdated'>;

type DHISUserFormGroupContent = {
  id: FormControl<DHISUserFormRawValue['id'] | NewDHISUser['id']>;
  code: FormControl<DHISUserFormRawValue['code']>;
  name: FormControl<DHISUserFormRawValue['name']>;
  displayName: FormControl<DHISUserFormRawValue['displayName']>;
  username: FormControl<DHISUserFormRawValue['username']>;
  lastLogin: FormControl<DHISUserFormRawValue['lastLogin']>;
  email: FormControl<DHISUserFormRawValue['email']>;
  phoneNumber: FormControl<DHISUserFormRawValue['phoneNumber']>;
  disabled: FormControl<DHISUserFormRawValue['disabled']>;
  passwordLastUpdated: FormControl<DHISUserFormRawValue['passwordLastUpdated']>;
  created: FormControl<DHISUserFormRawValue['created']>;
  lastUpdated: FormControl<DHISUserFormRawValue['lastUpdated']>;
  track: FormControl<DHISUserFormRawValue['track']>;
};

export type DHISUserFormGroup = FormGroup<DHISUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DHISUserFormService {
  createDHISUserFormGroup(dHISUser: DHISUserFormGroupInput = { id: null }): DHISUserFormGroup {
    const dHISUserRawValue = this.convertDHISUserToDHISUserRawValue({
      ...this.getFormDefaults(),
      ...dHISUser,
    });
    return new FormGroup<DHISUserFormGroupContent>({
      id: new FormControl(
        { value: dHISUserRawValue.id, disabled: dHISUserRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(dHISUserRawValue.code),
      name: new FormControl(dHISUserRawValue.name, {
        validators: [Validators.required],
      }),
      displayName: new FormControl(dHISUserRawValue.displayName),
      username: new FormControl(dHISUserRawValue.username, {
        validators: [Validators.required],
      }),
      lastLogin: new FormControl(dHISUserRawValue.lastLogin),
      email: new FormControl(dHISUserRawValue.email),
      phoneNumber: new FormControl(dHISUserRawValue.phoneNumber),
      disabled: new FormControl(dHISUserRawValue.disabled),
      passwordLastUpdated: new FormControl(dHISUserRawValue.passwordLastUpdated),
      created: new FormControl(dHISUserRawValue.created),
      lastUpdated: new FormControl(dHISUserRawValue.lastUpdated),
      track: new FormControl(dHISUserRawValue.track, {
        validators: [Validators.required],
      }),
    });
  }

  getDHISUser(form: DHISUserFormGroup): IDHISUser | NewDHISUser {
    return this.convertDHISUserRawValueToDHISUser(form.getRawValue() as DHISUserFormRawValue | NewDHISUserFormRawValue);
  }

  resetForm(form: DHISUserFormGroup, dHISUser: DHISUserFormGroupInput): void {
    const dHISUserRawValue = this.convertDHISUserToDHISUserRawValue({ ...this.getFormDefaults(), ...dHISUser });
    form.reset(
      {
        ...dHISUserRawValue,
        id: { value: dHISUserRawValue.id, disabled: dHISUserRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DHISUserFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastLogin: currentTime,
      disabled: false,
      passwordLastUpdated: currentTime,
      created: currentTime,
      lastUpdated: currentTime,
    };
  }

  private convertDHISUserRawValueToDHISUser(rawDHISUser: DHISUserFormRawValue | NewDHISUserFormRawValue): IDHISUser | NewDHISUser {
    return {
      ...rawDHISUser,
      lastLogin: dayjs(rawDHISUser.lastLogin, DATE_TIME_FORMAT),
      passwordLastUpdated: dayjs(rawDHISUser.passwordLastUpdated, DATE_TIME_FORMAT),
      created: dayjs(rawDHISUser.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawDHISUser.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertDHISUserToDHISUserRawValue(
    dHISUser: IDHISUser | (Partial<NewDHISUser> & DHISUserFormDefaults)
  ): DHISUserFormRawValue | PartialWithRequiredKeyOf<NewDHISUserFormRawValue> {
    return {
      ...dHISUser,
      lastLogin: dHISUser.lastLogin ? dHISUser.lastLogin.format(DATE_TIME_FORMAT) : undefined,
      passwordLastUpdated: dHISUser.passwordLastUpdated ? dHISUser.passwordLastUpdated.format(DATE_TIME_FORMAT) : undefined,
      created: dHISUser.created ? dHISUser.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: dHISUser.lastUpdated ? dHISUser.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
