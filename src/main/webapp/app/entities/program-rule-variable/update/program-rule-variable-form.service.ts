import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProgramRuleVariable, NewProgramRuleVariable } from '../program-rule-variable.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgramRuleVariable for edit and NewProgramRuleVariableFormGroupInput for create.
 */
type ProgramRuleVariableFormGroupInput = IProgramRuleVariable | PartialWithRequiredKeyOf<NewProgramRuleVariable>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProgramRuleVariable | NewProgramRuleVariable> = Omit<T, 'lastUpdated' | 'created'> & {
  lastUpdated?: string | null;
  created?: string | null;
};

type ProgramRuleVariableFormRawValue = FormValueOf<IProgramRuleVariable>;

type NewProgramRuleVariableFormRawValue = FormValueOf<NewProgramRuleVariable>;

type ProgramRuleVariableFormDefaults = Pick<NewProgramRuleVariable, 'id' | 'lastUpdated' | 'created' | 'useCodeForOptionSet'>;

type ProgramRuleVariableFormGroupContent = {
  id: FormControl<ProgramRuleVariableFormRawValue['id'] | NewProgramRuleVariable['id']>;
  lastUpdated: FormControl<ProgramRuleVariableFormRawValue['lastUpdated']>;
  created: FormControl<ProgramRuleVariableFormRawValue['created']>;
  name: FormControl<ProgramRuleVariableFormRawValue['name']>;
  displayName: FormControl<ProgramRuleVariableFormRawValue['displayName']>;
  programRuleVariableSourceType: FormControl<ProgramRuleVariableFormRawValue['programRuleVariableSourceType']>;
  useCodeForOptionSet: FormControl<ProgramRuleVariableFormRawValue['useCodeForOptionSet']>;
  track: FormControl<ProgramRuleVariableFormRawValue['track']>;
  project: FormControl<ProgramRuleVariableFormRawValue['project']>;
  createdBy: FormControl<ProgramRuleVariableFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<ProgramRuleVariableFormRawValue['lastUpdatedBy']>;
  program: FormControl<ProgramRuleVariableFormRawValue['program']>;
  trackedEntityAttribute: FormControl<ProgramRuleVariableFormRawValue['trackedEntityAttribute']>;
  dataElement: FormControl<ProgramRuleVariableFormRawValue['dataElement']>;
};

export type ProgramRuleVariableFormGroup = FormGroup<ProgramRuleVariableFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramRuleVariableFormService {
  createProgramRuleVariableFormGroup(programRuleVariable: ProgramRuleVariableFormGroupInput = { id: null }): ProgramRuleVariableFormGroup {
    const programRuleVariableRawValue = this.convertProgramRuleVariableToProgramRuleVariableRawValue({
      ...this.getFormDefaults(),
      ...programRuleVariable,
    });
    return new FormGroup<ProgramRuleVariableFormGroupContent>({
      id: new FormControl(
        { value: programRuleVariableRawValue.id, disabled: programRuleVariableRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lastUpdated: new FormControl(programRuleVariableRawValue.lastUpdated, {
        validators: [Validators.required],
      }),
      created: new FormControl(programRuleVariableRawValue.created, {
        validators: [Validators.required],
      }),
      name: new FormControl(programRuleVariableRawValue.name, {
        validators: [Validators.required],
      }),
      displayName: new FormControl(programRuleVariableRawValue.displayName),
      programRuleVariableSourceType: new FormControl(programRuleVariableRawValue.programRuleVariableSourceType),
      useCodeForOptionSet: new FormControl(programRuleVariableRawValue.useCodeForOptionSet),
      track: new FormControl(programRuleVariableRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(programRuleVariableRawValue.project),
      createdBy: new FormControl(programRuleVariableRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(programRuleVariableRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      program: new FormControl(programRuleVariableRawValue.program, {
        validators: [Validators.required],
      }),
      trackedEntityAttribute: new FormControl(programRuleVariableRawValue.trackedEntityAttribute),
      dataElement: new FormControl(programRuleVariableRawValue.dataElement),
    });
  }

  getProgramRuleVariable(form: ProgramRuleVariableFormGroup): IProgramRuleVariable | NewProgramRuleVariable {
    return this.convertProgramRuleVariableRawValueToProgramRuleVariable(
      form.getRawValue() as ProgramRuleVariableFormRawValue | NewProgramRuleVariableFormRawValue
    );
  }

  resetForm(form: ProgramRuleVariableFormGroup, programRuleVariable: ProgramRuleVariableFormGroupInput): void {
    const programRuleVariableRawValue = this.convertProgramRuleVariableToProgramRuleVariableRawValue({
      ...this.getFormDefaults(),
      ...programRuleVariable,
    });
    form.reset(
      {
        ...programRuleVariableRawValue,
        id: { value: programRuleVariableRawValue.id, disabled: programRuleVariableRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProgramRuleVariableFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastUpdated: currentTime,
      created: currentTime,
      useCodeForOptionSet: false,
    };
  }

  private convertProgramRuleVariableRawValueToProgramRuleVariable(
    rawProgramRuleVariable: ProgramRuleVariableFormRawValue | NewProgramRuleVariableFormRawValue
  ): IProgramRuleVariable | NewProgramRuleVariable {
    return {
      ...rawProgramRuleVariable,
      lastUpdated: dayjs(rawProgramRuleVariable.lastUpdated, DATE_TIME_FORMAT),
      created: dayjs(rawProgramRuleVariable.created, DATE_TIME_FORMAT),
    };
  }

  private convertProgramRuleVariableToProgramRuleVariableRawValue(
    programRuleVariable: IProgramRuleVariable | (Partial<NewProgramRuleVariable> & ProgramRuleVariableFormDefaults)
  ): ProgramRuleVariableFormRawValue | PartialWithRequiredKeyOf<NewProgramRuleVariableFormRawValue> {
    return {
      ...programRuleVariable,
      lastUpdated: programRuleVariable.lastUpdated ? programRuleVariable.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      created: programRuleVariable.created ? programRuleVariable.created.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
