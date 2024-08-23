import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITrackedEntityAttribute, NewTrackedEntityAttribute } from '../tracked-entity-attribute.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrackedEntityAttribute for edit and NewTrackedEntityAttributeFormGroupInput for create.
 */
type TrackedEntityAttributeFormGroupInput = ITrackedEntityAttribute | PartialWithRequiredKeyOf<NewTrackedEntityAttribute>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITrackedEntityAttribute | NewTrackedEntityAttribute> = Omit<T, 'lastUpdated' | 'created'> & {
  lastUpdated?: string | null;
  created?: string | null;
};

type TrackedEntityAttributeFormRawValue = FormValueOf<ITrackedEntityAttribute>;

type NewTrackedEntityAttributeFormRawValue = FormValueOf<NewTrackedEntityAttribute>;

type TrackedEntityAttributeFormDefaults = Pick<
  NewTrackedEntityAttribute,
  | 'id'
  | 'lastUpdated'
  | 'created'
  | 'generated'
  | 'confidential'
  | 'uniquee'
  | 'displayInListNoProgram'
  | 'skipSynchronization'
  | 'displayOnVisitSchedule'
  | 'orgunitScope'
  | 'inherit'
  | 'optionSetValue'
>;

type TrackedEntityAttributeFormGroupContent = {
  id: FormControl<TrackedEntityAttributeFormRawValue['id'] | NewTrackedEntityAttribute['id']>;
  lastUpdated: FormControl<TrackedEntityAttributeFormRawValue['lastUpdated']>;
  created: FormControl<TrackedEntityAttributeFormRawValue['created']>;
  name: FormControl<TrackedEntityAttributeFormRawValue['name']>;
  shortName: FormControl<TrackedEntityAttributeFormRawValue['shortName']>;
  generated: FormControl<TrackedEntityAttributeFormRawValue['generated']>;
  valueType: FormControl<TrackedEntityAttributeFormRawValue['valueType']>;
  confidential: FormControl<TrackedEntityAttributeFormRawValue['confidential']>;
  displayFormName: FormControl<TrackedEntityAttributeFormRawValue['displayFormName']>;
  uniquee: FormControl<TrackedEntityAttributeFormRawValue['uniquee']>;
  dimensionItemType: FormControl<TrackedEntityAttributeFormRawValue['dimensionItemType']>;
  aggregationType: FormControl<TrackedEntityAttributeFormRawValue['aggregationType']>;
  displayInListNoProgram: FormControl<TrackedEntityAttributeFormRawValue['displayInListNoProgram']>;
  displayName: FormControl<TrackedEntityAttributeFormRawValue['displayName']>;
  patterne: FormControl<TrackedEntityAttributeFormRawValue['patterne']>;
  skipSynchronization: FormControl<TrackedEntityAttributeFormRawValue['skipSynchronization']>;
  displayShortName: FormControl<TrackedEntityAttributeFormRawValue['displayShortName']>;
  periodOffset: FormControl<TrackedEntityAttributeFormRawValue['periodOffset']>;
  displayOnVisitSchedule: FormControl<TrackedEntityAttributeFormRawValue['displayOnVisitSchedule']>;
  formName: FormControl<TrackedEntityAttributeFormRawValue['formName']>;
  orgunitScope: FormControl<TrackedEntityAttributeFormRawValue['orgunitScope']>;
  dimensionItem: FormControl<TrackedEntityAttributeFormRawValue['dimensionItem']>;
  inherit: FormControl<TrackedEntityAttributeFormRawValue['inherit']>;
  optionSetValue: FormControl<TrackedEntityAttributeFormRawValue['optionSetValue']>;
  track: FormControl<TrackedEntityAttributeFormRawValue['track']>;
  project: FormControl<TrackedEntityAttributeFormRawValue['project']>;
  createdBy: FormControl<TrackedEntityAttributeFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<TrackedEntityAttributeFormRawValue['lastUpdatedBy']>;
  optionSet: FormControl<TrackedEntityAttributeFormRawValue['optionSet']>;
};

export type TrackedEntityAttributeFormGroup = FormGroup<TrackedEntityAttributeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrackedEntityAttributeFormService {
  createTrackedEntityAttributeFormGroup(
    trackedEntityAttribute: TrackedEntityAttributeFormGroupInput = { id: null },
  ): TrackedEntityAttributeFormGroup {
    const trackedEntityAttributeRawValue = this.convertTrackedEntityAttributeToTrackedEntityAttributeRawValue({
      ...this.getFormDefaults(),
      ...trackedEntityAttribute,
    });
    return new FormGroup<TrackedEntityAttributeFormGroupContent>({
      id: new FormControl(
        { value: trackedEntityAttributeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lastUpdated: new FormControl(trackedEntityAttributeRawValue.lastUpdated),
      created: new FormControl(trackedEntityAttributeRawValue.created),
      name: new FormControl(trackedEntityAttributeRawValue.name),
      shortName: new FormControl(trackedEntityAttributeRawValue.shortName),
      generated: new FormControl(trackedEntityAttributeRawValue.generated),
      valueType: new FormControl(trackedEntityAttributeRawValue.valueType),
      confidential: new FormControl(trackedEntityAttributeRawValue.confidential),
      displayFormName: new FormControl(trackedEntityAttributeRawValue.displayFormName),
      uniquee: new FormControl(trackedEntityAttributeRawValue.uniquee),
      dimensionItemType: new FormControl(trackedEntityAttributeRawValue.dimensionItemType),
      aggregationType: new FormControl(trackedEntityAttributeRawValue.aggregationType),
      displayInListNoProgram: new FormControl(trackedEntityAttributeRawValue.displayInListNoProgram),
      displayName: new FormControl(trackedEntityAttributeRawValue.displayName),
      patterne: new FormControl(trackedEntityAttributeRawValue.patterne),
      skipSynchronization: new FormControl(trackedEntityAttributeRawValue.skipSynchronization),
      displayShortName: new FormControl(trackedEntityAttributeRawValue.displayShortName),
      periodOffset: new FormControl(trackedEntityAttributeRawValue.periodOffset),
      displayOnVisitSchedule: new FormControl(trackedEntityAttributeRawValue.displayOnVisitSchedule),
      formName: new FormControl(trackedEntityAttributeRawValue.formName),
      orgunitScope: new FormControl(trackedEntityAttributeRawValue.orgunitScope),
      dimensionItem: new FormControl(trackedEntityAttributeRawValue.dimensionItem),
      inherit: new FormControl(trackedEntityAttributeRawValue.inherit),
      optionSetValue: new FormControl(trackedEntityAttributeRawValue.optionSetValue),
      track: new FormControl(trackedEntityAttributeRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(trackedEntityAttributeRawValue.project),
      createdBy: new FormControl(trackedEntityAttributeRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(trackedEntityAttributeRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      optionSet: new FormControl(trackedEntityAttributeRawValue.optionSet),
    });
  }

  getTrackedEntityAttribute(form: TrackedEntityAttributeFormGroup): ITrackedEntityAttribute | NewTrackedEntityAttribute {
    return this.convertTrackedEntityAttributeRawValueToTrackedEntityAttribute(
      form.getRawValue() as TrackedEntityAttributeFormRawValue | NewTrackedEntityAttributeFormRawValue,
    );
  }

  resetForm(form: TrackedEntityAttributeFormGroup, trackedEntityAttribute: TrackedEntityAttributeFormGroupInput): void {
    const trackedEntityAttributeRawValue = this.convertTrackedEntityAttributeToTrackedEntityAttributeRawValue({
      ...this.getFormDefaults(),
      ...trackedEntityAttribute,
    });
    form.reset(
      {
        ...trackedEntityAttributeRawValue,
        id: { value: trackedEntityAttributeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TrackedEntityAttributeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastUpdated: currentTime,
      created: currentTime,
      generated: false,
      confidential: false,
      uniquee: false,
      displayInListNoProgram: false,
      skipSynchronization: false,
      displayOnVisitSchedule: false,
      orgunitScope: false,
      inherit: false,
      optionSetValue: false,
    };
  }

  private convertTrackedEntityAttributeRawValueToTrackedEntityAttribute(
    rawTrackedEntityAttribute: TrackedEntityAttributeFormRawValue | NewTrackedEntityAttributeFormRawValue,
  ): ITrackedEntityAttribute | NewTrackedEntityAttribute {
    return {
      ...rawTrackedEntityAttribute,
      lastUpdated: dayjs(rawTrackedEntityAttribute.lastUpdated, DATE_TIME_FORMAT),
      created: dayjs(rawTrackedEntityAttribute.created, DATE_TIME_FORMAT),
    };
  }

  private convertTrackedEntityAttributeToTrackedEntityAttributeRawValue(
    trackedEntityAttribute: ITrackedEntityAttribute | (Partial<NewTrackedEntityAttribute> & TrackedEntityAttributeFormDefaults),
  ): TrackedEntityAttributeFormRawValue | PartialWithRequiredKeyOf<NewTrackedEntityAttributeFormRawValue> {
    return {
      ...trackedEntityAttribute,
      lastUpdated: trackedEntityAttribute.lastUpdated ? trackedEntityAttribute.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      created: trackedEntityAttribute.created ? trackedEntityAttribute.created.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
