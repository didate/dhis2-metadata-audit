import dayjs from 'dayjs/esm';

import { IProgramRuleVariable, NewProgramRuleVariable } from './program-rule-variable.model';

export const sampleWithRequiredData: IProgramRuleVariable = {
  id: 'fd667f76-bee9-41ed-94bf-bfc4f628a862',
  lastUpdated: dayjs('2024-08-22T05:14'),
  created: dayjs('2024-08-22T18:37'),
  name: 'rire au-dessous de',
  track: 'NONE',
};

export const sampleWithPartialData: IProgramRuleVariable = {
  id: 'a261c665-5dfe-47d1-813b-e082d81b335c',
  lastUpdated: dayjs('2024-08-22T08:13'),
  created: dayjs('2024-08-22T21:04'),
  name: 'crac modeler plic',
  useCodeForOptionSet: true,
  track: 'NEW',
};

export const sampleWithFullData: IProgramRuleVariable = {
  id: '900af34a-e011-4c8d-abb9-375686c428a8',
  lastUpdated: dayjs('2024-08-22T14:36'),
  created: dayjs('2024-08-23T01:07'),
  name: 'jusqu’à ce que',
  displayName: 'biathlète à condition que revenir',
  programRuleVariableSourceType: 'ronron de peur que',
  useCodeForOptionSet: true,
  track: 'UPDATE',
};

export const sampleWithNewData: NewProgramRuleVariable = {
  lastUpdated: dayjs('2024-08-22T14:46'),
  created: dayjs('2024-08-22T21:48'),
  name: 'adopter bouger collègue',
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
