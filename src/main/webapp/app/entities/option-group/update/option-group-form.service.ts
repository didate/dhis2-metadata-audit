import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IOptionGroup, NewOptionGroup } from '../option-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOptionGroup for edit and NewOptionGroupFormGroupInput for create.
 */
type OptionGroupFormGroupInput = IOptionGroup | PartialWithRequiredKeyOf<NewOptionGroup>;

type OptionGroupFormDefaults = Pick<NewOptionGroup, 'id'>;

type OptionGroupFormGroupContent = {
  id: FormControl<IOptionGroup['id'] | NewOptionGroup['id']>;
};

export type OptionGroupFormGroup = FormGroup<OptionGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OptionGroupFormService {
  createOptionGroupFormGroup(optionGroup: OptionGroupFormGroupInput = { id: null }): OptionGroupFormGroup {
    const optionGroupRawValue = {
      ...this.getFormDefaults(),
      ...optionGroup,
    };
    return new FormGroup<OptionGroupFormGroupContent>({
      id: new FormControl(
        { value: optionGroupRawValue.id, disabled: optionGroupRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
    });
  }

  getOptionGroup(form: OptionGroupFormGroup): IOptionGroup | NewOptionGroup {
    if (form.controls.id.disabled) {
      // form.value returns id with null value for FormGroup with only one FormControl
      return {
        id: null,
      };
    }
    return form.getRawValue() as IOptionGroup | NewOptionGroup;
  }

  resetForm(form: OptionGroupFormGroup, optionGroup: OptionGroupFormGroupInput): void {
    const optionGroupRawValue = { ...this.getFormDefaults(), ...optionGroup };
    form.reset(
      {
        ...optionGroupRawValue,
        id: { value: optionGroupRawValue.id, disabled: optionGroupRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OptionGroupFormDefaults {
    return {
      id: null,
    };
  }
}
