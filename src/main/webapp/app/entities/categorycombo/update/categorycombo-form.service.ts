import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorycombo, NewCategorycombo } from '../categorycombo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorycombo for edit and NewCategorycomboFormGroupInput for create.
 */
type CategorycomboFormGroupInput = ICategorycombo | PartialWithRequiredKeyOf<NewCategorycombo>;

type CategorycomboFormDefaults = Pick<NewCategorycombo, 'id'>;

type CategorycomboFormGroupContent = {
  id: FormControl<ICategorycombo['id'] | NewCategorycombo['id']>;
  name: FormControl<ICategorycombo['name']>;
};

export type CategorycomboFormGroup = FormGroup<CategorycomboFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategorycomboFormService {
  createCategorycomboFormGroup(categorycombo: CategorycomboFormGroupInput = { id: null }): CategorycomboFormGroup {
    const categorycomboRawValue = {
      ...this.getFormDefaults(),
      ...categorycombo,
    };
    return new FormGroup<CategorycomboFormGroupContent>({
      id: new FormControl(
        { value: categorycomboRawValue.id, disabled: categorycomboRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(categorycomboRawValue.name),
    });
  }

  getCategorycombo(form: CategorycomboFormGroup): ICategorycombo | NewCategorycombo {
    return form.getRawValue() as ICategorycombo | NewCategorycombo;
  }

  resetForm(form: CategorycomboFormGroup, categorycombo: CategorycomboFormGroupInput): void {
    const categorycomboRawValue = { ...this.getFormDefaults(), ...categorycombo };
    form.reset(
      {
        ...categorycomboRawValue,
        id: { value: categorycomboRawValue.id, disabled: categorycomboRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategorycomboFormDefaults {
    return {
      id: null,
    };
  }
}
