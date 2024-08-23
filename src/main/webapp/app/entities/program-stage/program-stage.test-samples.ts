import dayjs from 'dayjs/esm';

import { IProgramStage, NewProgramStage } from './program-stage.model';

export const sampleWithRequiredData: IProgramStage = {
  id: '1995de61-a161-41f7-9b9f-8706f7d52923',
};

export const sampleWithPartialData: IProgramStage = {
  id: '257bdd42-ec37-4ca2-97c3-b57b9a5202b8',
  created: dayjs('2024-08-23T08:35'),
  lastUpdated: dayjs('2024-08-23T12:15'),
  validationStrategy: 'Tilsitt back-end Account',
  featureType: 'Dollar c',
  preGenerateUID: false,
  remindCompleted: false,
  generatedByEnrollmentDate: false,
  openAfterEnrollment: true,
  sortOrder: 6834,
  hideDueDate: true,
  enableUserAssignment: false,
  referral: false,
  displayExecutionDateLabel: 'b',
  displayName: 'parse Account c',
  programStageDataElementsCount: 43587,
  programStageDataElementsContent: 12605,
};

export const sampleWithFullData: IProgramStage = {
  id: '9f678f37-3d57-4aca-b75c-984d549f64c2',
  name: 'calculate scale',
  created: dayjs('2024-08-23T00:28'),
  lastUpdated: dayjs('2024-08-22T23:47'),
  minDaysFromStart: 35380,
  executionDateLabel: 'deposit Intelligent yellow',
  autoGenerateEvent: true,
  validationStrategy: 'Indonésie Garden b',
  displayGenerateEventBox: true,
  featureType: 'program Rustic',
  blockEntryForm: false,
  preGenerateUID: true,
  remindCompleted: false,
  generatedByEnrollmentDate: false,
  allowGenerateNextVisit: true,
  openAfterEnrollment: false,
  sortOrder: 75471,
  hideDueDate: false,
  enableUserAssignment: true,
  referral: true,
  displayExecutionDateLabel: 'magnetic gold',
  formType: 'driver open-source Chat-qui-Pêche',
  displayFormName: 'Savings interface Superviseur',
  displayName: 'hybrid neural-net a',
  repeatable: true,
  programStageDataElementsCount: 11484,
  programStageDataElementsContent: 16420,
};

export const sampleWithNewData: NewProgramStage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
