import dayjs from 'dayjs/esm';

import { IProgramStage, NewProgramStage } from './program-stage.model';

export const sampleWithRequiredData: IProgramStage = {
  id: '48f32485-a29e-4136-9063-fcbabc301214',
};

export const sampleWithPartialData: IProgramStage = {
  id: 'f202b7c0-8ee6-4032-be64-c2244f7c6a3b',
  created: dayjs('2024-08-23T03:23'),
  lastUpdated: dayjs('2024-08-23T07:14'),
  autoGenerateEvent: false,
  validationStrategy: 'secours bzzz population du Québec',
  featureType: 'raide',
  blockEntryForm: true,
  generatedByEnrollmentDate: true,
  openAfterEnrollment: true,
  sortOrder: 770,
  hideDueDate: true,
  referral: true,
  displayExecutionDateLabel: 'personnel professionnel après lécher',
  displayName: 'touriste',
};

export const sampleWithFullData: IProgramStage = {
  id: 'b546b32a-cd53-4d26-979f-78c9d107f935',
  name: 'après que ouin sourire',
  created: dayjs('2024-08-23T10:38'),
  lastUpdated: dayjs('2024-08-23T17:35'),
  minDaysFromStart: 1967,
  executionDateLabel: 'minuscule vlan proche de',
  autoGenerateEvent: false,
  validationStrategy: 'préférer avant-hier jusque',
  displayGenerateEventBox: false,
  featureType: 'snif rose',
  blockEntryForm: false,
  preGenerateUID: true,
  remindCompleted: true,
  generatedByEnrollmentDate: false,
  allowGenerateNextVisit: true,
  openAfterEnrollment: false,
  sortOrder: 7896,
  hideDueDate: true,
  enableUserAssignment: false,
  referral: true,
  displayExecutionDateLabel: 'mélanger',
  formType: 'cot cot',
  displayFormName: 'quoique de la part de que',
  displayName: 'communauté étudiante souple',
  repeatable: true,
  programStageDataElementsCount: 2638,
  programStageDataElementsContent: 2268,
};

export const sampleWithNewData: NewProgramStage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
