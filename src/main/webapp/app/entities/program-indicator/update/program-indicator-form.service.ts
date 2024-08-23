import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProgramIndicator, NewProgramIndicator } from '../program-indicator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgramIndicator for edit and NewProgramIndicatorFormGroupInput for create.
 */
type ProgramIndicatorFormGroupInput = IProgramIndicator | PartialWithRequiredKeyOf<NewProgramIndicator>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProgramIndicator | NewProgramIndicator> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

type ProgramIndicatorFormRawValue = FormValueOf<IProgramIndicator>;

type NewProgramIndicatorFormRawValue = FormValueOf<NewProgramIndicator>;

type ProgramIndicatorFormDefaults = Pick<NewProgramIndicator, 'id' | 'created' | 'lastUpdated' | 'programs'>;

type ProgramIndicatorFormGroupContent = {
  id: FormControl<ProgramIndicatorFormRawValue['id'] | NewProgramIndicator['id']>;
  name: FormControl<ProgramIndicatorFormRawValue['name']>;
  created: FormControl<ProgramIndicatorFormRawValue['created']>;
  lastUpdated: FormControl<ProgramIndicatorFormRawValue['lastUpdated']>;
  shortName: FormControl<ProgramIndicatorFormRawValue['shortName']>;
  dimensionItemType: FormControl<ProgramIndicatorFormRawValue['dimensionItemType']>;
  expression: FormControl<ProgramIndicatorFormRawValue['expression']>;
  filter: FormControl<ProgramIndicatorFormRawValue['filter']>;
  analyticsType: FormControl<ProgramIndicatorFormRawValue['analyticsType']>;
  dimensionItem: FormControl<ProgramIndicatorFormRawValue['dimensionItem']>;
  displayShortName: FormControl<ProgramIndicatorFormRawValue['displayShortName']>;
  displayName: FormControl<ProgramIndicatorFormRawValue['displayName']>;
  displayFormName: FormControl<ProgramIndicatorFormRawValue['displayFormName']>;
  track: FormControl<ProgramIndicatorFormRawValue['track']>;
  createdBy: FormControl<ProgramIndicatorFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<ProgramIndicatorFormRawValue['lastUpdatedBy']>;
  program: FormControl<ProgramIndicatorFormRawValue['program']>;
  programs: FormControl<ProgramIndicatorFormRawValue['programs']>;
};

export type ProgramIndicatorFormGroup = FormGroup<ProgramIndicatorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramIndicatorFormService {
  createProgramIndicatorFormGroup(programIndicator: ProgramIndicatorFormGroupInput = { id: null }): ProgramIndicatorFormGroup {
    const programIndicatorRawValue = this.convertProgramIndicatorToProgramIndicatorRawValue({
      ...this.getFormDefaults(),
      ...programIndicator,
    });
    return new FormGroup<ProgramIndicatorFormGroupContent>({
      id: new FormControl(
        { value: programIndicatorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(programIndicatorRawValue.name, {
        validators: [Validators.required],
      }),
      created: new FormControl(programIndicatorRawValue.created, {
        validators: [Validators.required],
      }),
      lastUpdated: new FormControl(programIndicatorRawValue.lastUpdated, {
        validators: [Validators.required],
      }),
      shortName: new FormControl(programIndicatorRawValue.shortName),
      dimensionItemType: new FormControl(programIndicatorRawValue.dimensionItemType),
      expression: new FormControl(programIndicatorRawValue.expression),
      filter: new FormControl(programIndicatorRawValue.filter),
      analyticsType: new FormControl(programIndicatorRawValue.analyticsType),
      dimensionItem: new FormControl(programIndicatorRawValue.dimensionItem),
      displayShortName: new FormControl(programIndicatorRawValue.displayShortName),
      displayName: new FormControl(programIndicatorRawValue.displayName),
      displayFormName: new FormControl(programIndicatorRawValue.displayFormName),
      track: new FormControl(programIndicatorRawValue.track, {
        validators: [Validators.required],
      }),
      createdBy: new FormControl(programIndicatorRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(programIndicatorRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      program: new FormControl(programIndicatorRawValue.program, {
        validators: [Validators.required],
      }),
      programs: new FormControl(programIndicatorRawValue.programs ?? []),
    });
  }

  getProgramIndicator(form: ProgramIndicatorFormGroup): IProgramIndicator | NewProgramIndicator {
    return this.convertProgramIndicatorRawValueToProgramIndicator(
      form.getRawValue() as ProgramIndicatorFormRawValue | NewProgramIndicatorFormRawValue,
    );
  }

  resetForm(form: ProgramIndicatorFormGroup, programIndicator: ProgramIndicatorFormGroupInput): void {
    const programIndicatorRawValue = this.convertProgramIndicatorToProgramIndicatorRawValue({
      ...this.getFormDefaults(),
      ...programIndicator,
    });
    form.reset(
      {
        ...programIndicatorRawValue,
        id: { value: programIndicatorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProgramIndicatorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      lastUpdated: currentTime,
      programs: [],
    };
  }

  private convertProgramIndicatorRawValueToProgramIndicator(
    rawProgramIndicator: ProgramIndicatorFormRawValue | NewProgramIndicatorFormRawValue,
  ): IProgramIndicator | NewProgramIndicator {
    return {
      ...rawProgramIndicator,
      created: dayjs(rawProgramIndicator.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawProgramIndicator.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertProgramIndicatorToProgramIndicatorRawValue(
    programIndicator: IProgramIndicator | (Partial<NewProgramIndicator> & ProgramIndicatorFormDefaults),
  ): ProgramIndicatorFormRawValue | PartialWithRequiredKeyOf<NewProgramIndicatorFormRawValue> {
    return {
      ...programIndicator,
      created: programIndicator.created ? programIndicator.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: programIndicator.lastUpdated ? programIndicator.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      programs: programIndicator.programs ?? [],
    };
  }
}
