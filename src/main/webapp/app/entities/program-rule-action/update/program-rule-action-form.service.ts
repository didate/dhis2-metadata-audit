import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProgramRuleAction, NewProgramRuleAction } from '../program-rule-action.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgramRuleAction for edit and NewProgramRuleActionFormGroupInput for create.
 */
type ProgramRuleActionFormGroupInput = IProgramRuleAction | PartialWithRequiredKeyOf<NewProgramRuleAction>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProgramRuleAction | NewProgramRuleAction> = Omit<T, 'lastUpdated'> & {
  lastUpdated?: string | null;
};

type ProgramRuleActionFormRawValue = FormValueOf<IProgramRuleAction>;

type NewProgramRuleActionFormRawValue = FormValueOf<NewProgramRuleAction>;

type ProgramRuleActionFormDefaults = Pick<NewProgramRuleAction, 'id' | 'lastUpdated'>;

type ProgramRuleActionFormGroupContent = {
  id: FormControl<ProgramRuleActionFormRawValue['id'] | NewProgramRuleAction['id']>;
  lastUpdated: FormControl<ProgramRuleActionFormRawValue['lastUpdated']>;
  created: FormControl<ProgramRuleActionFormRawValue['created']>;
  programRuleActionType: FormControl<ProgramRuleActionFormRawValue['programRuleActionType']>;
  evaluationTime: FormControl<ProgramRuleActionFormRawValue['evaluationTime']>;
  data: FormControl<ProgramRuleActionFormRawValue['data']>;
  templateUid: FormControl<ProgramRuleActionFormRawValue['templateUid']>;
  content: FormControl<ProgramRuleActionFormRawValue['content']>;
  displayContent: FormControl<ProgramRuleActionFormRawValue['displayContent']>;
  track: FormControl<ProgramRuleActionFormRawValue['track']>;
  project: FormControl<ProgramRuleActionFormRawValue['project']>;
  createdBy: FormControl<ProgramRuleActionFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<ProgramRuleActionFormRawValue['lastUpdatedBy']>;
  programRule: FormControl<ProgramRuleActionFormRawValue['programRule']>;
  trackedEntityAttribute: FormControl<ProgramRuleActionFormRawValue['trackedEntityAttribute']>;
  dataElement: FormControl<ProgramRuleActionFormRawValue['dataElement']>;
  optionGroup: FormControl<ProgramRuleActionFormRawValue['optionGroup']>;
};

export type ProgramRuleActionFormGroup = FormGroup<ProgramRuleActionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramRuleActionFormService {
  createProgramRuleActionFormGroup(programRuleAction: ProgramRuleActionFormGroupInput = { id: null }): ProgramRuleActionFormGroup {
    const programRuleActionRawValue = this.convertProgramRuleActionToProgramRuleActionRawValue({
      ...this.getFormDefaults(),
      ...programRuleAction,
    });
    return new FormGroup<ProgramRuleActionFormGroupContent>({
      id: new FormControl(
        { value: programRuleActionRawValue.id, disabled: programRuleActionRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      lastUpdated: new FormControl(programRuleActionRawValue.lastUpdated),
      created: new FormControl(programRuleActionRawValue.created),
      programRuleActionType: new FormControl(programRuleActionRawValue.programRuleActionType),
      evaluationTime: new FormControl(programRuleActionRawValue.evaluationTime),
      data: new FormControl(programRuleActionRawValue.data),
      templateUid: new FormControl(programRuleActionRawValue.templateUid),
      content: new FormControl(programRuleActionRawValue.content),
      displayContent: new FormControl(programRuleActionRawValue.displayContent),
      track: new FormControl(programRuleActionRawValue.track, {
        validators: [Validators.required],
      }),
      project: new FormControl(programRuleActionRawValue.project),
      createdBy: new FormControl(programRuleActionRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(programRuleActionRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      programRule: new FormControl(programRuleActionRawValue.programRule, {
        validators: [Validators.required],
      }),
      trackedEntityAttribute: new FormControl(programRuleActionRawValue.trackedEntityAttribute),
      dataElement: new FormControl(programRuleActionRawValue.dataElement),
      optionGroup: new FormControl(programRuleActionRawValue.optionGroup),
    });
  }

  getProgramRuleAction(form: ProgramRuleActionFormGroup): IProgramRuleAction | NewProgramRuleAction {
    return this.convertProgramRuleActionRawValueToProgramRuleAction(
      form.getRawValue() as ProgramRuleActionFormRawValue | NewProgramRuleActionFormRawValue
    );
  }

  resetForm(form: ProgramRuleActionFormGroup, programRuleAction: ProgramRuleActionFormGroupInput): void {
    const programRuleActionRawValue = this.convertProgramRuleActionToProgramRuleActionRawValue({
      ...this.getFormDefaults(),
      ...programRuleAction,
    });
    form.reset(
      {
        ...programRuleActionRawValue,
        id: { value: programRuleActionRawValue.id, disabled: programRuleActionRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProgramRuleActionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastUpdated: currentTime,
    };
  }

  private convertProgramRuleActionRawValueToProgramRuleAction(
    rawProgramRuleAction: ProgramRuleActionFormRawValue | NewProgramRuleActionFormRawValue
  ): IProgramRuleAction | NewProgramRuleAction {
    return {
      ...rawProgramRuleAction,
      lastUpdated: dayjs(rawProgramRuleAction.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertProgramRuleActionToProgramRuleActionRawValue(
    programRuleAction: IProgramRuleAction | (Partial<NewProgramRuleAction> & ProgramRuleActionFormDefaults)
  ): ProgramRuleActionFormRawValue | PartialWithRequiredKeyOf<NewProgramRuleActionFormRawValue> {
    return {
      ...programRuleAction,
      lastUpdated: programRuleAction.lastUpdated ? programRuleAction.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
