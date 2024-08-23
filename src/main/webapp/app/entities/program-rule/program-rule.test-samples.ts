import dayjs from 'dayjs/esm';

import { IProgramRule, NewProgramRule } from './program-rule.model';

export const sampleWithRequiredData: IProgramRule = {
  id: '53ff3d5e-392d-4ed7-9864-474649c678c2',
  track: 'UPDATE',
};

export const sampleWithPartialData: IProgramRule = {
  id: '9fd80979-1ef4-4315-a0ab-fb78edef033f',
  created: dayjs('2024-08-22T15:47'),
  priority: 29923,
  track: 'NONE',
};

export const sampleWithFullData: IProgramRule = {
  id: 'd3e01f79-d2b5-43a4-9ef7-6b176b4766b8',
  lastUpdated: dayjs('2024-08-22T15:05'),
  created: dayjs('2024-08-22T10:20'),
  name: 'athlète',
  displayName: 'afin que perplexe doucement',
  priority: 6532,
  condition: 'cultiver à partir de ouch',
  track: 'NEW',
};

export const sampleWithNewData: NewProgramRule = {
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
