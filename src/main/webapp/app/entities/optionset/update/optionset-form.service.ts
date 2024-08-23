import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOptionset, NewOptionset } from '../optionset.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOptionset for edit and NewOptionsetFormGroupInput for create.
 */
type OptionsetFormGroupInput = IOptionset | PartialWithRequiredKeyOf<NewOptionset>;

type OptionsetFormDefaults = Pick<NewOptionset, 'id'>;

type OptionsetFormGroupContent = {
  id: FormControl<IOptionset['id'] | NewOptionset['id']>;
  name: FormControl<IOptionset['name']>;
};

export type OptionsetFormGroup = FormGroup<OptionsetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OptionsetFormService {
  createOptionsetFormGroup(optionset: OptionsetFormGroupInput = { id: null }): OptionsetFormGroup {
    const optionsetRawValue = {
      ...this.getFormDefaults(),
      ...optionset,
    };
    return new FormGroup<OptionsetFormGroupContent>({
      id: new FormControl(
        { value: optionsetRawValue.id, disabled: optionsetRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(optionsetRawValue.name),
    });
  }

  getOptionset(form: OptionsetFormGroup): IOptionset | NewOptionset {
    return form.getRawValue() as IOptionset | NewOptionset;
  }

  resetForm(form: OptionsetFormGroup, optionset: OptionsetFormGroupInput): void {
    const optionsetRawValue = { ...this.getFormDefaults(), ...optionset };
    form.reset(
      {
        ...optionsetRawValue,
        id: { value: optionsetRawValue.id, disabled: optionsetRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OptionsetFormDefaults {
    return {
      id: null,
    };
  }
}
