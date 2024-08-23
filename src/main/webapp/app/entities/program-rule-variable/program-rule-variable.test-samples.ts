import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IProgramRuleVariable, NewProgramRuleVariable } from './program-rule-variable.model';

export const sampleWithRequiredData: IProgramRuleVariable = {
  id: '57554e4c-e0e4-42d9-a8b4-5cb93ae61684',
  lastUpdated: dayjs('2024-08-23T01:14'),
  created: dayjs('2024-08-23T03:09'),
  name: 'Rubber',
  track: TypeTrack['UPDATE'],
};

export const sampleWithPartialData: IProgramRuleVariable = {
  id: 'a8e52d52-028e-40b8-a3ac-6057a56d1a7d',
  lastUpdated: dayjs('2024-08-22T22:13'),
  created: dayjs('2024-08-23T16:52'),
  name: 'override transmitting',
  track: TypeTrack['UPDATE'],
};

export const sampleWithFullData: IProgramRuleVariable = {
  id: '66bcb121-7026-45f0-922f-40c91f91f0e6',
  lastUpdated: dayjs('2024-08-23T09:51'),
  created: dayjs('2024-08-23T05:15'),
  name: 'Équateur program 1080p',
  displayName: 'La',
  programRuleVariableSourceType: 'Wooden Midi-Pyrénées Singapore',
  useCodeForOptionSet: true,
  track: TypeTrack['UPDATE'],
};

export const sampleWithNewData: NewProgramRuleVariable = {
  lastUpdated: dayjs('2024-08-23T05:22'),
  created: dayjs('2024-08-23T16:30'),
  name: 'interface Cotton',
  track: TypeTrack['NEW'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
