import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOrganisationUnit, NewOrganisationUnit } from '../organisation-unit.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrganisationUnit for edit and NewOrganisationUnitFormGroupInput for create.
 */
type OrganisationUnitFormGroupInput = IOrganisationUnit | PartialWithRequiredKeyOf<NewOrganisationUnit>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOrganisationUnit | NewOrganisationUnit> = Omit<T, 'created' | 'lastUpdated' | 'openingDate'> & {
  created?: string | null;
  lastUpdated?: string | null;
  openingDate?: string | null;
};

type OrganisationUnitFormRawValue = FormValueOf<IOrganisationUnit>;

type NewOrganisationUnitFormRawValue = FormValueOf<NewOrganisationUnit>;

type OrganisationUnitFormDefaults = Pick<NewOrganisationUnit, 'id' | 'created' | 'lastUpdated' | 'openingDate' | 'programs' | 'dataSets'>;

type OrganisationUnitFormGroupContent = {
  id: FormControl<OrganisationUnitFormRawValue['id'] | NewOrganisationUnit['id']>;
  name: FormControl<OrganisationUnitFormRawValue['name']>;
  created: FormControl<OrganisationUnitFormRawValue['created']>;
  lastUpdated: FormControl<OrganisationUnitFormRawValue['lastUpdated']>;
  path: FormControl<OrganisationUnitFormRawValue['path']>;
  openingDate: FormControl<OrganisationUnitFormRawValue['openingDate']>;
  level: FormControl<OrganisationUnitFormRawValue['level']>;
  track: FormControl<OrganisationUnitFormRawValue['track']>;
  createdBy: FormControl<OrganisationUnitFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<OrganisationUnitFormRawValue['lastUpdatedBy']>;
  programs: FormControl<OrganisationUnitFormRawValue['programs']>;
  dataSets: FormControl<OrganisationUnitFormRawValue['dataSets']>;
};

export type OrganisationUnitFormGroup = FormGroup<OrganisationUnitFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrganisationUnitFormService {
  createOrganisationUnitFormGroup(organisationUnit: OrganisationUnitFormGroupInput = { id: null }): OrganisationUnitFormGroup {
    const organisationUnitRawValue = this.convertOrganisationUnitToOrganisationUnitRawValue({
      ...this.getFormDefaults(),
      ...organisationUnit,
    });
    return new FormGroup<OrganisationUnitFormGroupContent>({
      id: new FormControl(
        { value: organisationUnitRawValue.id, disabled: organisationUnitRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(organisationUnitRawValue.name, {
        validators: [Validators.required],
      }),
      created: new FormControl(organisationUnitRawValue.created, {
        validators: [Validators.required],
      }),
      lastUpdated: new FormControl(organisationUnitRawValue.lastUpdated, {
        validators: [Validators.required],
      }),
      path: new FormControl(organisationUnitRawValue.path),
      openingDate: new FormControl(organisationUnitRawValue.openingDate, {
        validators: [Validators.required],
      }),
      level: new FormControl(organisationUnitRawValue.level),
      track: new FormControl(organisationUnitRawValue.track, {
        validators: [Validators.required],
      }),
      createdBy: new FormControl(organisationUnitRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(organisationUnitRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      programs: new FormControl(organisationUnitRawValue.programs ?? []),
      dataSets: new FormControl(organisationUnitRawValue.dataSets ?? []),
    });
  }

  getOrganisationUnit(form: OrganisationUnitFormGroup): IOrganisationUnit | NewOrganisationUnit {
    return this.convertOrganisationUnitRawValueToOrganisationUnit(
      form.getRawValue() as OrganisationUnitFormRawValue | NewOrganisationUnitFormRawValue
    );
  }

  resetForm(form: OrganisationUnitFormGroup, organisationUnit: OrganisationUnitFormGroupInput): void {
    const organisationUnitRawValue = this.convertOrganisationUnitToOrganisationUnitRawValue({
      ...this.getFormDefaults(),
      ...organisationUnit,
    });
    form.reset(
      {
        ...organisationUnitRawValue,
        id: { value: organisationUnitRawValue.id, disabled: organisationUnitRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrganisationUnitFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      lastUpdated: currentTime,
      openingDate: currentTime,
      programs: [],
      dataSets: [],
    };
  }

  private convertOrganisationUnitRawValueToOrganisationUnit(
    rawOrganisationUnit: OrganisationUnitFormRawValue | NewOrganisationUnitFormRawValue
  ): IOrganisationUnit | NewOrganisationUnit {
    return {
      ...rawOrganisationUnit,
      created: dayjs(rawOrganisationUnit.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawOrganisationUnit.lastUpdated, DATE_TIME_FORMAT),
      openingDate: dayjs(rawOrganisationUnit.openingDate, DATE_TIME_FORMAT),
    };
  }

  private convertOrganisationUnitToOrganisationUnitRawValue(
    organisationUnit: IOrganisationUnit | (Partial<NewOrganisationUnit> & OrganisationUnitFormDefaults)
  ): OrganisationUnitFormRawValue | PartialWithRequiredKeyOf<NewOrganisationUnitFormRawValue> {
    return {
      ...organisationUnit,
      created: organisationUnit.created ? organisationUnit.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: organisationUnit.lastUpdated ? organisationUnit.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      openingDate: organisationUnit.openingDate ? organisationUnit.openingDate.format(DATE_TIME_FORMAT) : undefined,
      programs: organisationUnit.programs ?? [],
      dataSets: organisationUnit.dataSets ?? [],
    };
  }
}
