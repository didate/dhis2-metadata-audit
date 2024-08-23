import dayjs from 'dayjs/esm';

import { IProgramRule, NewProgramRule } from './program-rule.model';

export const sampleWithRequiredData: IProgramRule = {
  id: '866f6612-571c-42c8-bb5b-81cb9c72969d',
  track: 'UPDATE',
};

export const sampleWithPartialData: IProgramRule = {
  id: '2322faa9-d6cc-45ab-80df-fc9a387b9691',
  name: 'relever avant que valoir',
  displayName: 'à seule fin de',
  condition: 'chut obliger adversaire',
  track: 'NEW',
};

export const sampleWithFullData: IProgramRule = {
  id: '554e5cdc-3d34-4c59-b546-39c74a4c539a',
  lastUpdated: dayjs('2024-08-22T21:04'),
  created: dayjs('2024-08-22T14:40'),
  name: 'naître afin que',
  displayName: 'conseil municipal de crainte que',
  priority: 16411,
  condition: 'ouf',
  track: 'NEW',
};

export const sampleWithNewData: NewProgramRule = {
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
