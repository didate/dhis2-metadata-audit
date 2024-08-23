import dayjs from 'dayjs/esm';

import { IProgramRuleVariable, NewProgramRuleVariable } from './program-rule-variable.model';

export const sampleWithRequiredData: IProgramRuleVariable = {
  id: '4dd70957-8230-4ad2-be02-c14a4ce78fe5',
  lastUpdated: dayjs('2024-08-22T22:57'),
  created: dayjs('2024-08-22T14:16'),
  name: 'adepte',
  track: 'NONE',
};

export const sampleWithPartialData: IProgramRuleVariable = {
  id: '1f2968b9-1c13-40df-9322-2264742c56b0',
  lastUpdated: dayjs('2024-08-22T13:49'),
  created: dayjs('2024-08-22T19:35'),
  name: 'tchou tchouu',
  displayName: 'affranchir dynamique',
  programRuleVariableSourceType: 'aussitôt que organiser',
  track: 'NEW',
};

export const sampleWithFullData: IProgramRuleVariable = {
  id: '915b2a69-023d-4ce8-acc3-25295ce44266',
  lastUpdated: dayjs('2024-08-22T03:23'),
  created: dayjs('2024-08-22T23:51'),
  name: 'favoriser oh terne',
  displayName: 'dans vaste sans',
  programRuleVariableSourceType: 'prestataire de services',
  useCodeForOptionSet: true,
  track: 'NONE',
};

export const sampleWithNewData: NewProgramRuleVariable = {
  lastUpdated: dayjs('2024-08-23T00:13'),
  created: dayjs('2024-08-22T09:37'),
  name: 'espiègle à partir de',
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
