import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIndicator, NewIndicator } from '../indicator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIndicator for edit and NewIndicatorFormGroupInput for create.
 */
type IndicatorFormGroupInput = IIndicator | PartialWithRequiredKeyOf<NewIndicator>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIndicator | NewIndicator> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

type IndicatorFormRawValue = FormValueOf<IIndicator>;

type NewIndicatorFormRawValue = FormValueOf<NewIndicator>;

type IndicatorFormDefaults = Pick<NewIndicator, 'id' | 'created' | 'lastUpdated' | 'annualized'>;

type IndicatorFormGroupContent = {
  id: FormControl<IndicatorFormRawValue['id'] | NewIndicator['id']>;
  name: FormControl<IndicatorFormRawValue['name']>;
  shortName: FormControl<IndicatorFormRawValue['shortName']>;
  displayShortName: FormControl<IndicatorFormRawValue['displayShortName']>;
  displayName: FormControl<IndicatorFormRawValue['displayName']>;
  displayFormName: FormControl<IndicatorFormRawValue['displayFormName']>;
  created: FormControl<IndicatorFormRawValue['created']>;
  lastUpdated: FormControl<IndicatorFormRawValue['lastUpdated']>;
  publicAccess: FormControl<IndicatorFormRawValue['publicAccess']>;
  dimensionItemType: FormControl<IndicatorFormRawValue['dimensionItemType']>;
  annualized: FormControl<IndicatorFormRawValue['annualized']>;
  numerator: FormControl<IndicatorFormRawValue['numerator']>;
  numeratorDescription: FormControl<IndicatorFormRawValue['numeratorDescription']>;
  denominator: FormControl<IndicatorFormRawValue['denominator']>;
  denominatorDescription: FormControl<IndicatorFormRawValue['denominatorDescription']>;
  displayNumeratorDescription: FormControl<IndicatorFormRawValue['displayNumeratorDescription']>;
  displayDenominatorDescription: FormControl<IndicatorFormRawValue['displayDenominatorDescription']>;
  dimensionItem: FormControl<IndicatorFormRawValue['dimensionItem']>;
  track: FormControl<IndicatorFormRawValue['track']>;
  project: FormControl<IndicatorFormRawValue['project']>;
  createdBy: FormControl<IndicatorFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<IndicatorFormRawValue['lastUpdatedBy']>;
  indicatorType: FormControl<IndicatorFormRawValue['indicatorType']>;
};

export type IndicatorFormGroup = FormGroup<IndicatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IndicatorFormService {
  createIndicatorFormGroup(indicator: IndicatorFormGroupInput = { id: null }): IndicatorFormGroup {
    const indicatorRawValue = this.convertIndicatorToIndicatorRawValue({
      ...this.getFormDefaults(),
      ...indicator,
    });
    return new FormGroup<IndicatorFormGroupContent>({
      id: new FormControl(
        { value: indicatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(indicatorRawValue.name, {
        validators: [Validators.required],
      }),
      shortName: new FormControl(indicatorRawValue.shortName, {
        validators: [Validators.required],
      }),
      displayShortName: new FormControl(indicatorRawValue.displayShortName),
      displayName: new FormControl(indicatorRawValue.displayName),
      displayFormName: new FormControl(indicatorRawValue.displayFormName),
      created: new FormControl(indicatorRawValue.created, {
        validators: [Validators.required],
      }),
      lastUpdated: new FormControl(indicatorRawValue.lastUpdated, {
        validators: [Validators.required],
      }),
      publicAccess: new FormControl(indicatorRawValue.publicAccess, {
        validators: [Validators.required],
      }),
      dimensionItemType: new FormControl(indicatorRawValue.dimensionItemType, {
        validators: [Validators.required],
      }),
      annualized: new FormControl(indicatorRawValue.annualized, {
        validators: [Validators.required],
      }),
      numerator: new FormControl(indicatorRawValue.numerator),
      numeratorDescription: new FormControl(indicatorRawValue.numeratorDescription),
      denominator: new FormControl(indicatorRawValue.denominator),
      denominatorDescription: new FormControl(indicatorRawValue.denominatorDescription),
      displayNumeratorDescription: new FormControl(indicatorRawValue.displayNumeratorDescription),
      displayDenominatorDescription: new FormControl(indicatorRawValue.displayDenominatorDescription),
      dimensionItem: new FormControl(indicatorRawValue.dimensionItem),
      track: new FormControl(indicatorRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(indicatorRawValue.project),
      createdBy: new FormControl(indicatorRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(indicatorRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      indicatorType: new FormControl(indicatorRawValue.indicatorType, {
        validators: [Validators.required],
      }),
    });
  }

  getIndicator(form: IndicatorFormGroup): IIndicator | NewIndicator {
    return this.convertIndicatorRawValueToIndicator(form.getRawValue() as IndicatorFormRawValue | NewIndicatorFormRawValue);
  }

  resetForm(form: IndicatorFormGroup, indicator: IndicatorFormGroupInput): void {
    const indicatorRawValue = this.convertIndicatorToIndicatorRawValue({ ...this.getFormDefaults(), ...indicator });
    form.reset(
      {
        ...indicatorRawValue,
        id: { value: indicatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): IndicatorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      lastUpdated: currentTime,
      annualized: false,
    };
  }

  private convertIndicatorRawValueToIndicator(rawIndicator: IndicatorFormRawValue | NewIndicatorFormRawValue): IIndicator | NewIndicator {
    return {
      ...rawIndicator,
      created: dayjs(rawIndicator.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawIndicator.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertIndicatorToIndicatorRawValue(
    indicator: IIndicator | (Partial<NewIndicator> & IndicatorFormDefaults),
  ): IndicatorFormRawValue | PartialWithRequiredKeyOf<NewIndicatorFormRawValue> {
    return {
      ...indicator,
      created: indicator.created ? indicator.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: indicator.lastUpdated ? indicator.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
