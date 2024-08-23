import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDataelement, NewDataelement } from '../dataelement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataelement for edit and NewDataelementFormGroupInput for create.
 */
type DataelementFormGroupInput = IDataelement | PartialWithRequiredKeyOf<NewDataelement>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDataelement | NewDataelement> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

type DataelementFormRawValue = FormValueOf<IDataelement>;

type NewDataelementFormRawValue = FormValueOf<NewDataelement>;

type DataelementFormDefaults = Pick<NewDataelement, 'id' | 'created' | 'lastUpdated' | 'zeroIsSignificant' | 'datasets' | 'programStages'>;

type DataelementFormGroupContent = {
  id: FormControl<DataelementFormRawValue['id'] | NewDataelement['id']>;
  name: FormControl<DataelementFormRawValue['name']>;
  shortName: FormControl<DataelementFormRawValue['shortName']>;
  formName: FormControl<DataelementFormRawValue['formName']>;
  description: FormControl<DataelementFormRawValue['description']>;
  displayShortName: FormControl<DataelementFormRawValue['displayShortName']>;
  displayName: FormControl<DataelementFormRawValue['displayName']>;
  displayFormName: FormControl<DataelementFormRawValue['displayFormName']>;
  created: FormControl<DataelementFormRawValue['created']>;
  lastUpdated: FormControl<DataelementFormRawValue['lastUpdated']>;
  publicAccess: FormControl<DataelementFormRawValue['publicAccess']>;
  dimensionItemType: FormControl<DataelementFormRawValue['dimensionItemType']>;
  aggregationType: FormControl<DataelementFormRawValue['aggregationType']>;
  valueType: FormControl<DataelementFormRawValue['valueType']>;
  domainType: FormControl<DataelementFormRawValue['domainType']>;
  zeroIsSignificant: FormControl<DataelementFormRawValue['zeroIsSignificant']>;
  optionSetValue: FormControl<DataelementFormRawValue['optionSetValue']>;
  dimensionItem: FormControl<DataelementFormRawValue['dimensionItem']>;
  track: FormControl<DataelementFormRawValue['track']>;
  project: FormControl<DataelementFormRawValue['project']>;
  createdBy: FormControl<DataelementFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<DataelementFormRawValue['lastUpdatedBy']>;
  categoryCombo: FormControl<DataelementFormRawValue['categoryCombo']>;
  optionSet: FormControl<DataelementFormRawValue['optionSet']>;
  datasets: FormControl<DataelementFormRawValue['datasets']>;
  programStages: FormControl<DataelementFormRawValue['programStages']>;
};

export type DataelementFormGroup = FormGroup<DataelementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DataelementFormService {
  createDataelementFormGroup(dataelement: DataelementFormGroupInput = { id: null }): DataelementFormGroup {
    const dataelementRawValue = this.convertDataelementToDataelementRawValue({
      ...this.getFormDefaults(),
      ...dataelement,
    });
    return new FormGroup<DataelementFormGroupContent>({
      id: new FormControl(
        { value: dataelementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(dataelementRawValue.name, {
        validators: [Validators.required],
      }),
      shortName: new FormControl(dataelementRawValue.shortName, {
        validators: [Validators.required],
      }),
      formName: new FormControl(dataelementRawValue.formName),
      description: new FormControl(dataelementRawValue.description),
      displayShortName: new FormControl(dataelementRawValue.displayShortName),
      displayName: new FormControl(dataelementRawValue.displayName),
      displayFormName: new FormControl(dataelementRawValue.displayFormName),
      created: new FormControl(dataelementRawValue.created, {
        validators: [Validators.required],
      }),
      lastUpdated: new FormControl(dataelementRawValue.lastUpdated, {
        validators: [Validators.required],
      }),
      publicAccess: new FormControl(dataelementRawValue.publicAccess, {
        validators: [Validators.required],
      }),
      dimensionItemType: new FormControl(dataelementRawValue.dimensionItemType, {
        validators: [Validators.required],
      }),
      aggregationType: new FormControl(dataelementRawValue.aggregationType, {
        validators: [Validators.required],
      }),
      valueType: new FormControl(dataelementRawValue.valueType, {
        validators: [Validators.required],
      }),
      domainType: new FormControl(dataelementRawValue.domainType, {
        validators: [Validators.required],
      }),
      zeroIsSignificant: new FormControl(dataelementRawValue.zeroIsSignificant),
      optionSetValue: new FormControl(dataelementRawValue.optionSetValue),
      dimensionItem: new FormControl(dataelementRawValue.dimensionItem),
      track: new FormControl(dataelementRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(dataelementRawValue.project),
      createdBy: new FormControl(dataelementRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(dataelementRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      categoryCombo: new FormControl(dataelementRawValue.categoryCombo),
      optionSet: new FormControl(dataelementRawValue.optionSet),
      datasets: new FormControl(dataelementRawValue.datasets ?? []),
      programStages: new FormControl(dataelementRawValue.programStages ?? []),
    });
  }

  getDataelement(form: DataelementFormGroup): IDataelement | NewDataelement {
    return this.convertDataelementRawValueToDataelement(form.getRawValue() as DataelementFormRawValue | NewDataelementFormRawValue);
  }

  resetForm(form: DataelementFormGroup, dataelement: DataelementFormGroupInput): void {
    const dataelementRawValue = this.convertDataelementToDataelementRawValue({ ...this.getFormDefaults(), ...dataelement });
    form.reset(
      {
        ...dataelementRawValue,
        id: { value: dataelementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DataelementFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      lastUpdated: currentTime,
      zeroIsSignificant: false,
      datasets: [],
      programStages: [],
    };
  }

  private convertDataelementRawValueToDataelement(
    rawDataelement: DataelementFormRawValue | NewDataelementFormRawValue,
  ): IDataelement | NewDataelement {
    return {
      ...rawDataelement,
      created: dayjs(rawDataelement.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawDataelement.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertDataelementToDataelementRawValue(
    dataelement: IDataelement | (Partial<NewDataelement> & DataelementFormDefaults),
  ): DataelementFormRawValue | PartialWithRequiredKeyOf<NewDataelementFormRawValue> {
    return {
      ...dataelement,
      created: dataelement.created ? dataelement.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: dataelement.lastUpdated ? dataelement.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      datasets: dataelement.datasets ?? [],
      programStages: dataelement.programStages ?? [],
    };
  }
}
