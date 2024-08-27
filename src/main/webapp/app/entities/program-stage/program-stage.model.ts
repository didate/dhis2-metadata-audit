import dayjs from 'dayjs/esm';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';

export interface IProgramStage {
  id: string;
  name?: string | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  minDaysFromStart?: number | null;
  executionDateLabel?: string | null;
  autoGenerateEvent?: boolean | null;
  validationStrategy?: string | null;
  displayGenerateEventBox?: boolean | null;
  featureType?: string | null;
  blockEntryForm?: boolean | null;
  preGenerateUID?: boolean | null;
  remindCompleted?: boolean | null;
  generatedByEnrollmentDate?: boolean | null;
  allowGenerateNextVisit?: boolean | null;
  openAfterEnrollment?: boolean | null;
  sortOrder?: number | null;
  hideDueDate?: boolean | null;
  enableUserAssignment?: boolean | null;
  referral?: boolean | null;
  displayExecutionDateLabel?: string | null;
  formType?: string | null;
  displayFormName?: string | null;
  displayName?: string | null;
  repeatable?: boolean | null;
  programStageDataElementsCount?: number | null;
  programStageDataElementsContent?: number | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
  program?: Pick<IProgram, 'name'> | null;
  programStageDataElements?: Pick<IDataelement, 'id'>[] | null;
  programs?: Pick<IProgram, 'id'>[] | null;
}

export type NewProgramStage = Omit<IProgramStage, 'id'> & { id: null };
