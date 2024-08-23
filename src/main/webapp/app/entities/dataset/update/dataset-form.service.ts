import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDataset, NewDataset } from '../dataset.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataset for edit and NewDatasetFormGroupInput for create.
 */
type DatasetFormGroupInput = IDataset | PartialWithRequiredKeyOf<NewDataset>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDataset | NewDataset> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

type DatasetFormRawValue = FormValueOf<IDataset>;

type NewDatasetFormRawValue = FormValueOf<NewDataset>;

type DatasetFormDefaults = Pick<
  NewDataset,
  | 'id'
  | 'created'
  | 'lastUpdated'
  | 'fieldCombinationRequired'
  | 'validCompleteOnly'
  | 'noValueRequiresComment'
  | 'skipOffline'
  | 'dataElementDecoration'
  | 'renderAsTabs'
  | 'renderHorizontally'
  | 'compulsoryFieldsCompleteOnly'
  | 'dataSetElements'
  | 'indicators'
  | 'organisationUnits'
>;

type DatasetFormGroupContent = {
  id: FormControl<DatasetFormRawValue['id'] | NewDataset['id']>;
  name: FormControl<DatasetFormRawValue['name']>;
  created: FormControl<DatasetFormRawValue['created']>;
  lastUpdated: FormControl<DatasetFormRawValue['lastUpdated']>;
  shortName: FormControl<DatasetFormRawValue['shortName']>;
  description: FormControl<DatasetFormRawValue['description']>;
  dimensionItemType: FormControl<DatasetFormRawValue['dimensionItemType']>;
  periodType: FormControl<DatasetFormRawValue['periodType']>;
  mobile: FormControl<DatasetFormRawValue['mobile']>;
  version: FormControl<DatasetFormRawValue['version']>;
  expiryDays: FormControl<DatasetFormRawValue['expiryDays']>;
  timelyDays: FormControl<DatasetFormRawValue['timelyDays']>;
  notifyCompletingUser: FormControl<DatasetFormRawValue['notifyCompletingUser']>;
  openFuturePeriods: FormControl<DatasetFormRawValue['openFuturePeriods']>;
  openPeriodsAfterCoEndDate: FormControl<DatasetFormRawValue['openPeriodsAfterCoEndDate']>;
  fieldCombinationRequired: FormControl<DatasetFormRawValue['fieldCombinationRequired']>;
  validCompleteOnly: FormControl<DatasetFormRawValue['validCompleteOnly']>;
  noValueRequiresComment: FormControl<DatasetFormRawValue['noValueRequiresComment']>;
  skipOffline: FormControl<DatasetFormRawValue['skipOffline']>;
  dataElementDecoration: FormControl<DatasetFormRawValue['dataElementDecoration']>;
  renderAsTabs: FormControl<DatasetFormRawValue['renderAsTabs']>;
  renderHorizontally: FormControl<DatasetFormRawValue['renderHorizontally']>;
  compulsoryFieldsCompleteOnly: FormControl<DatasetFormRawValue['compulsoryFieldsCompleteOnly']>;
  formType: FormControl<DatasetFormRawValue['formType']>;
  displayName: FormControl<DatasetFormRawValue['displayName']>;
  dimensionItem: FormControl<DatasetFormRawValue['dimensionItem']>;
  displayShortName: FormControl<DatasetFormRawValue['displayShortName']>;
  displayDescription: FormControl<DatasetFormRawValue['displayDescription']>;
  displayFormName: FormControl<DatasetFormRawValue['displayFormName']>;
  dataSetElementsCount: FormControl<DatasetFormRawValue['dataSetElementsCount']>;
  indicatorsCount: FormControl<DatasetFormRawValue['indicatorsCount']>;
  organisationUnitsCount: FormControl<DatasetFormRawValue['organisationUnitsCount']>;
  dataSetElementsContent: FormControl<DatasetFormRawValue['dataSetElementsContent']>;
  indicatorsContent: FormControl<DatasetFormRawValue['indicatorsContent']>;
  organisationUnitsContent: FormControl<DatasetFormRawValue['organisationUnitsContent']>;
  track: FormControl<DatasetFormRawValue['track']>;
  project: FormControl<DatasetFormRawValue['project']>;
  createdBy: FormControl<DatasetFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<DatasetFormRawValue['lastUpdatedBy']>;
  categoryCombo: FormControl<DatasetFormRawValue['categoryCombo']>;
  dataSetElements: FormControl<DatasetFormRawValue['dataSetElements']>;
  indicators: FormControl<DatasetFormRawValue['indicators']>;
  organisationUnits: FormControl<DatasetFormRawValue['organisationUnits']>;
};

export type DatasetFormGroup = FormGroup<DatasetFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DatasetFormService {
  createDatasetFormGroup(dataset: DatasetFormGroupInput = { id: null }): DatasetFormGroup {
    const datasetRawValue = this.convertDatasetToDatasetRawValue({
      ...this.getFormDefaults(),
      ...dataset,
    });
    return new FormGroup<DatasetFormGroupContent>({
      id: new FormControl(
        { value: datasetRawValue.id, disabled: datasetRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(datasetRawValue.name),
      created: new FormControl(datasetRawValue.created, {
        validators: [Validators.required],
      }),
      lastUpdated: new FormControl(datasetRawValue.lastUpdated, {
        validators: [Validators.required],
      }),
      shortName: new FormControl(datasetRawValue.shortName),
      description: new FormControl(datasetRawValue.description),
      dimensionItemType: new FormControl(datasetRawValue.dimensionItemType),
      periodType: new FormControl(datasetRawValue.periodType),
      mobile: new FormControl(datasetRawValue.mobile),
      version: new FormControl(datasetRawValue.version),
      expiryDays: new FormControl(datasetRawValue.expiryDays),
      timelyDays: new FormControl(datasetRawValue.timelyDays),
      notifyCompletingUser: new FormControl(datasetRawValue.notifyCompletingUser),
      openFuturePeriods: new FormControl(datasetRawValue.openFuturePeriods),
      openPeriodsAfterCoEndDate: new FormControl(datasetRawValue.openPeriodsAfterCoEndDate),
      fieldCombinationRequired: new FormControl(datasetRawValue.fieldCombinationRequired),
      validCompleteOnly: new FormControl(datasetRawValue.validCompleteOnly),
      noValueRequiresComment: new FormControl(datasetRawValue.noValueRequiresComment),
      skipOffline: new FormControl(datasetRawValue.skipOffline),
      dataElementDecoration: new FormControl(datasetRawValue.dataElementDecoration),
      renderAsTabs: new FormControl(datasetRawValue.renderAsTabs),
      renderHorizontally: new FormControl(datasetRawValue.renderHorizontally),
      compulsoryFieldsCompleteOnly: new FormControl(datasetRawValue.compulsoryFieldsCompleteOnly),
      formType: new FormControl(datasetRawValue.formType),
      displayName: new FormControl(datasetRawValue.displayName),
      dimensionItem: new FormControl(datasetRawValue.dimensionItem),
      displayShortName: new FormControl(datasetRawValue.displayShortName),
      displayDescription: new FormControl(datasetRawValue.displayDescription),
      displayFormName: new FormControl(datasetRawValue.displayFormName),
      dataSetElementsCount: new FormControl(datasetRawValue.dataSetElementsCount),
      indicatorsCount: new FormControl(datasetRawValue.indicatorsCount),
      organisationUnitsCount: new FormControl(datasetRawValue.organisationUnitsCount),
      dataSetElementsContent: new FormControl(datasetRawValue.dataSetElementsContent),
      indicatorsContent: new FormControl(datasetRawValue.indicatorsContent),
      organisationUnitsContent: new FormControl(datasetRawValue.organisationUnitsContent),
      track: new FormControl(datasetRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(datasetRawValue.project),
      createdBy: new FormControl(datasetRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(datasetRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      categoryCombo: new FormControl(datasetRawValue.categoryCombo),
      dataSetElements: new FormControl(datasetRawValue.dataSetElements ?? []),
      indicators: new FormControl(datasetRawValue.indicators ?? []),
      organisationUnits: new FormControl(datasetRawValue.organisationUnits ?? []),
    });
  }

  getDataset(form: DatasetFormGroup): IDataset | NewDataset {
    return this.convertDatasetRawValueToDataset(form.getRawValue() as DatasetFormRawValue | NewDatasetFormRawValue);
  }

  resetForm(form: DatasetFormGroup, dataset: DatasetFormGroupInput): void {
    const datasetRawValue = this.convertDatasetToDatasetRawValue({ ...this.getFormDefaults(), ...dataset });
    form.reset(
      {
        ...datasetRawValue,
        id: { value: datasetRawValue.id, disabled: datasetRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DatasetFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      lastUpdated: currentTime,
      fieldCombinationRequired: false,
      validCompleteOnly: false,
      noValueRequiresComment: false,
      skipOffline: false,
      dataElementDecoration: false,
      renderAsTabs: false,
      renderHorizontally: false,
      compulsoryFieldsCompleteOnly: false,
      dataSetElements: [],
      indicators: [],
      organisationUnits: [],
    };
  }

  private convertDatasetRawValueToDataset(rawDataset: DatasetFormRawValue | NewDatasetFormRawValue): IDataset | NewDataset {
    return {
      ...rawDataset,
      created: dayjs(rawDataset.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawDataset.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertDatasetToDatasetRawValue(
    dataset: IDataset | (Partial<NewDataset> & DatasetFormDefaults)
  ): DatasetFormRawValue | PartialWithRequiredKeyOf<NewDatasetFormRawValue> {
    return {
      ...dataset,
      created: dataset.created ? dataset.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: dataset.lastUpdated ? dataset.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      dataSetElements: dataset.dataSetElements ?? [],
      indicators: dataset.indicators ?? [],
      organisationUnits: dataset.organisationUnits ?? [],
    };
  }
}
