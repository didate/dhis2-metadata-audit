import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProgramStage, NewProgramStage } from '../program-stage.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgramStage for edit and NewProgramStageFormGroupInput for create.
 */
type ProgramStageFormGroupInput = IProgramStage | PartialWithRequiredKeyOf<NewProgramStage>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProgramStage | NewProgramStage> = Omit<T, 'created' | 'lastUpdated'> & {
  created?: string | null;
  lastUpdated?: string | null;
};

type ProgramStageFormRawValue = FormValueOf<IProgramStage>;

type NewProgramStageFormRawValue = FormValueOf<NewProgramStage>;

type ProgramStageFormDefaults = Pick<
  NewProgramStage,
  | 'id'
  | 'created'
  | 'lastUpdated'
  | 'autoGenerateEvent'
  | 'displayGenerateEventBox'
  | 'blockEntryForm'
  | 'preGenerateUID'
  | 'remindCompleted'
  | 'generatedByEnrollmentDate'
  | 'allowGenerateNextVisit'
  | 'openAfterEnrollment'
  | 'hideDueDate'
  | 'enableUserAssignment'
  | 'referral'
  | 'repeatable'
  | 'programStageDataElements'
  | 'programs'
>;

type ProgramStageFormGroupContent = {
  id: FormControl<ProgramStageFormRawValue['id'] | NewProgramStage['id']>;
  name: FormControl<ProgramStageFormRawValue['name']>;
  created: FormControl<ProgramStageFormRawValue['created']>;
  lastUpdated: FormControl<ProgramStageFormRawValue['lastUpdated']>;
  minDaysFromStart: FormControl<ProgramStageFormRawValue['minDaysFromStart']>;
  executionDateLabel: FormControl<ProgramStageFormRawValue['executionDateLabel']>;
  autoGenerateEvent: FormControl<ProgramStageFormRawValue['autoGenerateEvent']>;
  validationStrategy: FormControl<ProgramStageFormRawValue['validationStrategy']>;
  displayGenerateEventBox: FormControl<ProgramStageFormRawValue['displayGenerateEventBox']>;
  featureType: FormControl<ProgramStageFormRawValue['featureType']>;
  blockEntryForm: FormControl<ProgramStageFormRawValue['blockEntryForm']>;
  preGenerateUID: FormControl<ProgramStageFormRawValue['preGenerateUID']>;
  remindCompleted: FormControl<ProgramStageFormRawValue['remindCompleted']>;
  generatedByEnrollmentDate: FormControl<ProgramStageFormRawValue['generatedByEnrollmentDate']>;
  allowGenerateNextVisit: FormControl<ProgramStageFormRawValue['allowGenerateNextVisit']>;
  openAfterEnrollment: FormControl<ProgramStageFormRawValue['openAfterEnrollment']>;
  sortOrder: FormControl<ProgramStageFormRawValue['sortOrder']>;
  hideDueDate: FormControl<ProgramStageFormRawValue['hideDueDate']>;
  enableUserAssignment: FormControl<ProgramStageFormRawValue['enableUserAssignment']>;
  referral: FormControl<ProgramStageFormRawValue['referral']>;
  displayExecutionDateLabel: FormControl<ProgramStageFormRawValue['displayExecutionDateLabel']>;
  formType: FormControl<ProgramStageFormRawValue['formType']>;
  displayFormName: FormControl<ProgramStageFormRawValue['displayFormName']>;
  displayName: FormControl<ProgramStageFormRawValue['displayName']>;
  repeatable: FormControl<ProgramStageFormRawValue['repeatable']>;
  programStageDataElementsCount: FormControl<ProgramStageFormRawValue['programStageDataElementsCount']>;
  programStageDataElementsContent: FormControl<ProgramStageFormRawValue['programStageDataElementsContent']>;
  createdBy: FormControl<ProgramStageFormRawValue['createdBy']>;
  lastUpdatedBy: FormControl<ProgramStageFormRawValue['lastUpdatedBy']>;
  program: FormControl<ProgramStageFormRawValue['program']>;
  programStageDataElements: FormControl<ProgramStageFormRawValue['programStageDataElements']>;
  programs: FormControl<ProgramStageFormRawValue['programs']>;
};

export type ProgramStageFormGroup = FormGroup<ProgramStageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramStageFormService {
  createProgramStageFormGroup(programStage: ProgramStageFormGroupInput = { id: null }): ProgramStageFormGroup {
    const programStageRawValue = this.convertProgramStageToProgramStageRawValue({
      ...this.getFormDefaults(),
      ...programStage,
    });
    return new FormGroup<ProgramStageFormGroupContent>({
      id: new FormControl(
        { value: programStageRawValue.id, disabled: programStageRawValue.id !== null },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(programStageRawValue.name),
      created: new FormControl(programStageRawValue.created),
      lastUpdated: new FormControl(programStageRawValue.lastUpdated),
      minDaysFromStart: new FormControl(programStageRawValue.minDaysFromStart),
      executionDateLabel: new FormControl(programStageRawValue.executionDateLabel),
      autoGenerateEvent: new FormControl(programStageRawValue.autoGenerateEvent),
      validationStrategy: new FormControl(programStageRawValue.validationStrategy),
      displayGenerateEventBox: new FormControl(programStageRawValue.displayGenerateEventBox),
      featureType: new FormControl(programStageRawValue.featureType),
      blockEntryForm: new FormControl(programStageRawValue.blockEntryForm),
      preGenerateUID: new FormControl(programStageRawValue.preGenerateUID),
      remindCompleted: new FormControl(programStageRawValue.remindCompleted),
      generatedByEnrollmentDate: new FormControl(programStageRawValue.generatedByEnrollmentDate),
      allowGenerateNextVisit: new FormControl(programStageRawValue.allowGenerateNextVisit),
      openAfterEnrollment: new FormControl(programStageRawValue.openAfterEnrollment),
      sortOrder: new FormControl(programStageRawValue.sortOrder),
      hideDueDate: new FormControl(programStageRawValue.hideDueDate),
      enableUserAssignment: new FormControl(programStageRawValue.enableUserAssignment),
      referral: new FormControl(programStageRawValue.referral),
      displayExecutionDateLabel: new FormControl(programStageRawValue.displayExecutionDateLabel),
      formType: new FormControl(programStageRawValue.formType),
      displayFormName: new FormControl(programStageRawValue.displayFormName),
      displayName: new FormControl(programStageRawValue.displayName),
      repeatable: new FormControl(programStageRawValue.repeatable),
      programStageDataElementsCount: new FormControl(programStageRawValue.programStageDataElementsCount),
      programStageDataElementsContent: new FormControl(programStageRawValue.programStageDataElementsContent),
      createdBy: new FormControl(programStageRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(programStageRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
      program: new FormControl(programStageRawValue.program, {
        validators: [Validators.required],
      }),
      programStageDataElements: new FormControl(programStageRawValue.programStageDataElements ?? []),
      programs: new FormControl(programStageRawValue.programs ?? []),
    });
  }

  getProgramStage(form: ProgramStageFormGroup): IProgramStage | NewProgramStage {
    return this.convertProgramStageRawValueToProgramStage(form.getRawValue() as ProgramStageFormRawValue | NewProgramStageFormRawValue);
  }

  resetForm(form: ProgramStageFormGroup, programStage: ProgramStageFormGroupInput): void {
    const programStageRawValue = this.convertProgramStageToProgramStageRawValue({ ...this.getFormDefaults(), ...programStage });
    form.reset(
      {
        ...programStageRawValue,
        id: { value: programStageRawValue.id, disabled: programStageRawValue.id !== null },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProgramStageFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      lastUpdated: currentTime,
      autoGenerateEvent: false,
      displayGenerateEventBox: false,
      blockEntryForm: false,
      preGenerateUID: false,
      remindCompleted: false,
      generatedByEnrollmentDate: false,
      allowGenerateNextVisit: false,
      openAfterEnrollment: false,
      hideDueDate: false,
      enableUserAssignment: false,
      referral: false,
      repeatable: false,
      programStageDataElements: [],
      programs: [],
    };
  }

  private convertProgramStageRawValueToProgramStage(
    rawProgramStage: ProgramStageFormRawValue | NewProgramStageFormRawValue
  ): IProgramStage | NewProgramStage {
    return {
      ...rawProgramStage,
      created: dayjs(rawProgramStage.created, DATE_TIME_FORMAT),
      lastUpdated: dayjs(rawProgramStage.lastUpdated, DATE_TIME_FORMAT),
    };
  }

  private convertProgramStageToProgramStageRawValue(
    programStage: IProgramStage | (Partial<NewProgramStage> & ProgramStageFormDefaults)
  ): ProgramStageFormRawValue | PartialWithRequiredKeyOf<NewProgramStageFormRawValue> {
    return {
      ...programStage,
      created: programStage.created ? programStage.created.format(DATE_TIME_FORMAT) : undefined,
      lastUpdated: programStage.lastUpdated ? programStage.lastUpdated.format(DATE_TIME_FORMAT) : undefined,
      programStageDataElements: programStage.programStageDataElements ?? [],
      programs: programStage.programs ?? [],
    };
  }
}
