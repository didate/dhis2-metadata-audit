import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProgramRule, NewProgramRule } from '../program-rule.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgramRule for edit and NewProgramRuleFormGroupInput for create.
 */
type ProgramRuleFormGroupInput = IProgramRule | PartialWithRequiredKeyOf<NewProgramRule>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProgramRule | NewProgramRule> = Omit<T, 'lastUpdated' | 'created'> & {
  lastUpdated?: string | null;
  created?: string | null;
};

type ProgramRuleFormRawValue = FormValueOf<IProgramRule>;

type NewProgramRuleFormRawValue = FormValueOf<NewProgramRule>;

type ProgramRuleFormDefaults = Pick<NewProgramRule, 'id' | 'lastUpdated' | 'created'>;

type ProgramRuleFormGroupContent = {
  id: FormControl<ProgramRuleFormRawValue['id'] | NewProgramRule['id']>;
  lastUpdated: FormControl<ProgramRuleFormRawValue['lastUpdated']>;
  created: FormControl<ProgramRuleFormRawValue['created']>;
  name: FormControl<ProgramRuleFormRawValue['name']>;
  displayName: FormControl<ProgramRuleFormRawValue['displayName']>;
  priority: FormControl<ProgramRuleFormRawValue['priority']>;
  condition: FormControl<ProgramRuleFormRawValue['condition']>;
  track: FormControl<ProgramRuleFormRawValue['track']>;
  project: FormControl<ProgramRuleFormRawValue['project']>;
  createdBy: FormControl<ProgramRuleFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<ProgramRuleFormRawValue['lastUpdatedBy']>;
  program: FormControl<ProgramRuleFormRawValue['program']>;
};

export type ProgramRuleFormGroup = FormGroup<ProgramRuleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramRuleFormService {
  createProgramRuleFormGroup(programRule: ProgramRuleFormGroupInput = { id: null }): ProgramRuleFormGroup {
    const programRuleRawValue = this.convertProgramRuleToProgramRuleRawValue({
      ...this.getFormDefaults(),
      ...programRule,
    });
    return new FormGroup<ProgramRuleFormGroupContent>({
      id: new FormControl(
        { value: programRuleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      lastUpdated: new FormControl(programRuleRawValue.lastUpdated),
      created: new FormControl(programRuleRawValue.created),
      name: new FormControl(programRuleRawValue.name),
      displayName: new FormControl(programRuleRawValue.displayName),
      priority: new FormControl(programRuleRawValue.priority),
      condition: new FormControl(programRuleRawValue.condition),
      track: new FormControl(programRuleRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(programRuleRawValue.project),
      createdBy: new FormControl(programRuleRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(programRuleRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      program: new FormControl(programRuleRawValue.program, {
        validators: [Validators.required],
      }),
    });
  }

  getProgramRule(form: ProgramRuleFormGroup): IProgramRule | NewProgramRule {
    return this.convertProgramRuleRawValueToProgramRule(form.getRawValue() as ProgramRuleFormRawValue | NewProgramRuleFormRawValue);
  }

  resetForm(form: ProgramRuleFormGroup, programRule: ProgramRuleFormGroupInput): void {
    const programRuleRawValue = this.convertProgramRuleToProgramRuleRawValue({ ...this.getFormDefaults(), ...programRule });
    form.reset(
      {
        ...programRuleRawValue,
        id: { value: programRuleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProgramRuleFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastUpdated: currentTime,
      created: currentTime,
    };
  }

  private convertProgramRuleRawValueToProgramRule(
    rawProgramRule: ProgramRuleFormRawValue | NewProgramRuleFormRawValue,
  ): IProgramRule | NewProgramRule {
    return {
      ...rawProgramRule,
      lastUpdated: dayjs(rawProgramRule.lastUpdated, DATE_TIME_FORMAT),
      created: dayjs(rawProgramRule.created, DATE_TIME_FORMAT),
    };
  }

  private convertProgramRuleToProgramRuleRawValue(
    programRule: IProgramRule | (Partial<NewProgramRule> & ProgramRuleFormDefaults),
  ): ProgramRuleFormRawValue | PartialWithRequiredKeyOf<NewProgramRuleFormRawValue> {
    return {
      ...programRule,
      lastUpdated: programRule.lastUpdated ? programRule.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      created: programRule.created ? programRule.created.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
