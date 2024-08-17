import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProgram, NewProgram } from '../program.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProgram for edit and NewProgramFormGroupInput for create.
 */
type ProgramFormGroupInput = IProgram | PartialWithRequiredKeyOf<NewProgram>;

type ProgramFormDefaults = Pick<
  NewProgram,
  | 'id'
  | 'displayIncidentDate'
  | 'ignoreOverdueEvents'
  | 'onlyEnrollOnce'
  | 'selectEnrollmentDatesInFuture'
  | 'selectIncidentDatesInFuture'
  | 'skipOffline'
  | 'displayFrontPageList'
  | 'useFirstStageDuringRegistration'
  | 'registration'
  | 'withoutRegistration'
>;

type ProgramFormGroupContent = {
  id: FormControl<IProgram['id'] | NewProgram['id']>;
  name: FormControl<IProgram['name']>;
  created: FormControl<IProgram['created']>;
  lastUpdated: FormControl<IProgram['lastUpdated']>;
  shortName: FormControl<IProgram['shortName']>;
  description: FormControl<IProgram['description']>;
  version: FormControl<IProgram['version']>;
  enrollmentDateLabel: FormControl<IProgram['enrollmentDateLabel']>;
  incidentDateLabel: FormControl<IProgram['incidentDateLabel']>;
  programType: FormControl<IProgram['programType']>;
  displayIncidentDate: FormControl<IProgram['displayIncidentDate']>;
  ignoreOverdueEvents: FormControl<IProgram['ignoreOverdueEvents']>;
  userRoles: FormControl<IProgram['userRoles']>;
  programIndicators: FormControl<IProgram['programIndicators']>;
  programRuleVariables: FormControl<IProgram['programRuleVariables']>;
  onlyEnrollOnce: FormControl<IProgram['onlyEnrollOnce']>;
  notificationTemplates: FormControl<IProgram['notificationTemplates']>;
  selectEnrollmentDatesInFuture: FormControl<IProgram['selectEnrollmentDatesInFuture']>;
  selectIncidentDatesInFuture: FormControl<IProgram['selectIncidentDatesInFuture']>;
  trackedEntityType: FormControl<IProgram['trackedEntityType']>;
  style: FormControl<IProgram['style']>;
  categoryCombo: FormControl<IProgram['categoryCombo']>;
  skipOffline: FormControl<IProgram['skipOffline']>;
  displayFrontPageList: FormControl<IProgram['displayFrontPageList']>;
  useFirstStageDuringRegistration: FormControl<IProgram['useFirstStageDuringRegistration']>;
  expiryDays: FormControl<IProgram['expiryDays']>;
  completeEventsExpiryDays: FormControl<IProgram['completeEventsExpiryDays']>;
  openDaysAfterCoEndDate: FormControl<IProgram['openDaysAfterCoEndDate']>;
  minAttributesRequiredToSearch: FormControl<IProgram['minAttributesRequiredToSearch']>;
  maxTeiCountToReturn: FormControl<IProgram['maxTeiCountToReturn']>;
  accessLevel: FormControl<IProgram['accessLevel']>;
  displayEnrollmentDateLabel: FormControl<IProgram['displayEnrollmentDateLabel']>;
  displayIncidentDateLabel: FormControl<IProgram['displayIncidentDateLabel']>;
  registration: FormControl<IProgram['registration']>;
  withoutRegistration: FormControl<IProgram['withoutRegistration']>;
  displayShortName: FormControl<IProgram['displayShortName']>;
  displayDescription: FormControl<IProgram['displayDescription']>;
  displayFormName: FormControl<IProgram['displayFormName']>;
  displayName: FormControl<IProgram['displayName']>;
  attributeValuesCount: FormControl<IProgram['attributeValuesCount']>;
  organisationUnitsCount: FormControl<IProgram['organisationUnitsCount']>;
  programStagesCount: FormControl<IProgram['programStagesCount']>;
  programSectionsCount: FormControl<IProgram['programSectionsCount']>;
  programTrackedEntityAttributesCount: FormControl<IProgram['programTrackedEntityAttributesCount']>;
  project: FormControl<IProgram['project']>;
  createdBy: FormControl<IProgram['createdBy']>;
  lastUpdatedBy: FormControl<IProgram['lastUpdatedBy']>;
};

export type ProgramFormGroup = FormGroup<ProgramFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProgramFormService {
  createProgramFormGroup(program: ProgramFormGroupInput = { id: null }): ProgramFormGroup {
    const programRawValue = {
      ...this.getFormDefaults(),
      ...program,
    };
    return new FormGroup<ProgramFormGroupContent>({
      id: new FormControl(
        { value: programRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(programRawValue.name),
      created: new FormControl(programRawValue.created),
      lastUpdated: new FormControl(programRawValue.lastUpdated),
      shortName: new FormControl(programRawValue.shortName),
      description: new FormControl(programRawValue.description),
      version: new FormControl(programRawValue.version),
      enrollmentDateLabel: new FormControl(programRawValue.enrollmentDateLabel),
      incidentDateLabel: new FormControl(programRawValue.incidentDateLabel),
      programType: new FormControl(programRawValue.programType),
      displayIncidentDate: new FormControl(programRawValue.displayIncidentDate),
      ignoreOverdueEvents: new FormControl(programRawValue.ignoreOverdueEvents),
      userRoles: new FormControl(programRawValue.userRoles),
      programIndicators: new FormControl(programRawValue.programIndicators),
      programRuleVariables: new FormControl(programRawValue.programRuleVariables),
      onlyEnrollOnce: new FormControl(programRawValue.onlyEnrollOnce),
      notificationTemplates: new FormControl(programRawValue.notificationTemplates),
      selectEnrollmentDatesInFuture: new FormControl(programRawValue.selectEnrollmentDatesInFuture),
      selectIncidentDatesInFuture: new FormControl(programRawValue.selectIncidentDatesInFuture),
      trackedEntityType: new FormControl(programRawValue.trackedEntityType),
      style: new FormControl(programRawValue.style),
      categoryCombo: new FormControl(programRawValue.categoryCombo),
      skipOffline: new FormControl(programRawValue.skipOffline),
      displayFrontPageList: new FormControl(programRawValue.displayFrontPageList),
      useFirstStageDuringRegistration: new FormControl(programRawValue.useFirstStageDuringRegistration),
      expiryDays: new FormControl(programRawValue.expiryDays),
      completeEventsExpiryDays: new FormControl(programRawValue.completeEventsExpiryDays),
      openDaysAfterCoEndDate: new FormControl(programRawValue.openDaysAfterCoEndDate),
      minAttributesRequiredToSearch: new FormControl(programRawValue.minAttributesRequiredToSearch),
      maxTeiCountToReturn: new FormControl(programRawValue.maxTeiCountToReturn),
      accessLevel: new FormControl(programRawValue.accessLevel),
      displayEnrollmentDateLabel: new FormControl(programRawValue.displayEnrollmentDateLabel),
      displayIncidentDateLabel: new FormControl(programRawValue.displayIncidentDateLabel),
      registration: new FormControl(programRawValue.registration),
      withoutRegistration: new FormControl(programRawValue.withoutRegistration),
      displayShortName: new FormControl(programRawValue.displayShortName),
      displayDescription: new FormControl(programRawValue.displayDescription),
      displayFormName: new FormControl(programRawValue.displayFormName),
      displayName: new FormControl(programRawValue.displayName),
      attributeValuesCount: new FormControl(programRawValue.attributeValuesCount),
      organisationUnitsCount: new FormControl(programRawValue.organisationUnitsCount),
      programStagesCount: new FormControl(programRawValue.programStagesCount),
      programSectionsCount: new FormControl(programRawValue.programSectionsCount),
      programTrackedEntityAttributesCount: new FormControl(programRawValue.programTrackedEntityAttributesCount),
      project: new FormControl(programRawValue.project),
      createdBy: new FormControl(programRawValue.createdBy, {
        validators: [Validators.required],
      }),
      lastUpdatedBy: new FormControl(programRawValue.lastUpdatedBy, {
        validators: [Validators.required],
      }),
    });
  }

  getProgram(form: ProgramFormGroup): IProgram | NewProgram {
    return form.getRawValue() as IProgram | NewProgram;
  }

  resetForm(form: ProgramFormGroup, program: ProgramFormGroupInput): void {
    const programRawValue = { ...this.getFormDefaults(), ...program };
    form.reset(
      {
        ...programRawValue,
        id: { value: programRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProgramFormDefaults {
    return {
      id: null,
      displayIncidentDate: false,
      ignoreOverdueEvents: false,
      onlyEnrollOnce: false,
      selectEnrollmentDatesInFuture: false,
      selectIncidentDatesInFuture: false,
      skipOffline: false,
      displayFrontPageList: false,
      useFirstStageDuringRegistration: false,
      registration: false,
      withoutRegistration: false,
    };
  }
}
