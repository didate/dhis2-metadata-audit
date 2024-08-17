import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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

type DHISUserFormDefaults = Pick<NewDHISUser, 'id'>;

type DHISUserFormGroupContent = {
  id: FormControl<IDHISUser['id'] | NewDHISUser['id']>;
  code: FormControl<IDHISUser['code']>;
  name: FormControl<IDHISUser['name']>;
  displayName: FormControl<IDHISUser['displayName']>;
  username: FormControl<IDHISUser['username']>;
};

export type DHISUserFormGroup = FormGroup<DHISUserFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DHISUserFormService {
  createDHISUserFormGroup(dHISUser: DHISUserFormGroupInput = { id: null }): DHISUserFormGroup {
    const dHISUserRawValue = {
      ...this.getFormDefaults(),
      ...dHISUser,
    };
    return new FormGroup<DHISUserFormGroupContent>({
      id: new FormControl(
        { value: dHISUserRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(dHISUserRawValue.code),
      name: new FormControl(dHISUserRawValue.name, {
        validators: [Validators.required],
      }),
      displayName: new FormControl(dHISUserRawValue.displayName),
      username: new FormControl(dHISUserRawValue.username, {
        validators: [Validators.required],
      }),
    });
  }

  getDHISUser(form: DHISUserFormGroup): IDHISUser | NewDHISUser {
    return form.getRawValue() as IDHISUser | NewDHISUser;
  }

  resetForm(form: DHISUserFormGroup, dHISUser: DHISUserFormGroupInput): void {
    const dHISUserRawValue = { ...this.getFormDefaults(), ...dHISUser };
    form.reset(
      {
        ...dHISUserRawValue,
        id: { value: dHISUserRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DHISUserFormDefaults {
    return {
      id: null,
    };
  }
}
