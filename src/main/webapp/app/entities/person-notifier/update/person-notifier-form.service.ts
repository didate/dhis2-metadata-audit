import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPersonNotifier, NewPersonNotifier } from '../person-notifier.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPersonNotifier for edit and NewPersonNotifierFormGroupInput for create.
 */
type PersonNotifierFormGroupInput = IPersonNotifier | PartialWithRequiredKeyOf<NewPersonNotifier>;

type PersonNotifierFormDefaults = Pick<NewPersonNotifier, 'id'>;

type PersonNotifierFormGroupContent = {
  id: FormControl<IPersonNotifier['id'] | NewPersonNotifier['id']>;
  personName: FormControl<IPersonNotifier['personName']>;
  personPhone: FormControl<IPersonNotifier['personPhone']>;
  personEmail: FormControl<IPersonNotifier['personEmail']>;
  personOrganization: FormControl<IPersonNotifier['personOrganization']>;
  project: FormControl<IPersonNotifier['project']>;
};

export type PersonNotifierFormGroup = FormGroup<PersonNotifierFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PersonNotifierFormService {
  createPersonNotifierFormGroup(personNotifier: PersonNotifierFormGroupInput = { id: null }): PersonNotifierFormGroup {
    const personNotifierRawValue = {
      ...this.getFormDefaults(),
      ...personNotifier,
    };
    return new FormGroup<PersonNotifierFormGroupContent>({
      id: new FormControl(
        { value: personNotifierRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      personName: new FormControl(personNotifierRawValue.personName, {
        validators: [Validators.required],
      }),
      personPhone: new FormControl(personNotifierRawValue.personPhone, {
        validators: [Validators.required],
      }),
      personEmail: new FormControl(personNotifierRawValue.personEmail, {
        validators: [Validators.required, Validators.pattern('^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$')],
      }),
      personOrganization: new FormControl(personNotifierRawValue.personOrganization, {
        validators: [Validators.required],
      }),
      project: new FormControl(personNotifierRawValue.project),
    });
  }

  getPersonNotifier(form: PersonNotifierFormGroup): IPersonNotifier | NewPersonNotifier {
    return form.getRawValue() as IPersonNotifier | NewPersonNotifier;
  }

  resetForm(form: PersonNotifierFormGroup, personNotifier: PersonNotifierFormGroupInput): void {
    const personNotifierRawValue = { ...this.getFormDefaults(), ...personNotifier };
    form.reset(
      {
        ...personNotifierRawValue,
        id: { value: personNotifierRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PersonNotifierFormDefaults {
    return {
      id: null,
    };
  }
}
