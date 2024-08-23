import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IProgramRule, NewProgramRule } from './program-rule.model';

export const sampleWithRequiredData: IProgramRule = {
  id: '7873e156-f01d-41a9-883c-3f9da74a345b',
  track: TypeTrack['NONE'],
};

export const sampleWithPartialData: IProgramRule = {
  id: 'ab458fb7-2eb5-4d7b-bfdf-c2f9b25753d6',
  lastUpdated: dayjs('2024-08-23T14:28'),
  created: dayjs('2024-08-23T20:21'),
  priority: 61133,
  condition: 'Rubber',
  track: TypeTrack['NONE'],
};

export const sampleWithFullData: IProgramRule = {
  id: 'c7e4bf3a-fb07-48ed-a2c9-b56d65a8b4bc',
  lastUpdated: dayjs('2024-08-23T03:52'),
  created: dayjs('2024-08-23T08:03'),
  name: 'Rustic implement Bahamian',
  displayName: 'invoice c workforce',
  priority: 44696,
  condition: 'c incubate robust',
  track: TypeTrack['UPDATE'],
};

export const sampleWithNewData: NewProgramRule = {
  track: TypeTrack['NONE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
