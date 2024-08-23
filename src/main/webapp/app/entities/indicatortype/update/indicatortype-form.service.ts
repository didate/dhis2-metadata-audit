import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIndicatortype, NewIndicatortype } from '../indicatortype.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIndicatortype for edit and NewIndicatortypeFormGroupInput for create.
 */
type IndicatortypeFormGroupInput = IIndicatortype | PartialWithRequiredKeyOf<NewIndicatortype>;

type IndicatortypeFormDefaults = Pick<NewIndicatortype, 'id'>;

type IndicatortypeFormGroupContent = {
  id: FormControl<IIndicatortype['id'] | NewIndicatortype['id']>;
  name: FormControl<IIndicatortype['name']>;
};

export type IndicatortypeFormGroup = FormGroup<IndicatortypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IndicatortypeFormService {
  createIndicatortypeFormGroup(indicatortype: IndicatortypeFormGroupInput = { id: null }): IndicatortypeFormGroup {
    const indicatortypeRawValue = {
      ...this.getFormDefaults(),
      ...indicatortype,
    };
    return new FormGroup<IndicatortypeFormGroupContent>({
      id: new FormControl(
        { value: indicatortypeRawValue.id, disabled: indicatortypeRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(indicatortypeRawValue.name),
    });
  }

  getIndicatortype(form: IndicatortypeFormGroup): IIndicatortype | NewIndicatortype {
    return form.getRawValue() as IIndicatortype | NewIndicatortype;
  }

  resetForm(form: IndicatortypeFormGroup, indicatortype: IndicatortypeFormGroupInput): void {
    const indicatortypeRawValue = { ...this.getFormDefaults(), ...indicatortype };
    form.reset(
      {
        ...indicatortypeRawValue,
        id: { value: indicatortypeRawValue.id, disabled: indicatortypeRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IndicatortypeFormDefaults {
    return {
      id: null,
    };
  }
}
