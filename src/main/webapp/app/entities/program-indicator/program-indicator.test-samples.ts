import dayjs from 'dayjs/esm';

import { IProgramIndicator, NewProgramIndicator } from './program-indicator.model';

export const sampleWithRequiredData: IProgramIndicator = {
  id: '66bca892-0212-4778-ad2e-1aeff41026c9',
  name: 'ronron de',
  created: dayjs('2024-08-22T21:56'),
  lastUpdated: dayjs('2024-08-22T23:16'),
  track: 'NEW',
};

export const sampleWithPartialData: IProgramIndicator = {
  id: '74ae256c-6e15-45d1-99fb-d33cb31742a6',
  name: 'diablement jadis plaider',
  created: dayjs('2024-08-23T03:08'),
  lastUpdated: dayjs('2024-08-22T21:04'),
  filter: 'toc-toc',
  analyticsType: 'bénéficier acquérir',
  displayShortName: 'certes vers même si',
  displayName: 'ha tellement blablabla',
  displayFormName: 'triangulaire avant de',
  track: 'NONE',
};

export const sampleWithFullData: IProgramIndicator = {
  id: 'babde4cb-2f72-40de-9942-26284c973e63',
  name: 'ding',
  created: dayjs('2024-08-23T17:08'),
  lastUpdated: dayjs('2024-08-23T09:48'),
  shortName: 'parvenir de par',
  dimensionItemType: 'neutre turquoise à raison de',
  expression: 'biathlète',
  filter: 'juriste gens',
  analyticsType: 'par suite de exiger',
  dimensionItem: 'vu que dans la mesure où',
  displayShortName: 'remonter triangulaire',
  displayName: 'alentour poser aigre',
  displayFormName: 'après',
  track: 'NONE',
};

export const sampleWithNewData: NewProgramIndicator = {
  name: 'plic ouch cadre',
  created: dayjs('2024-08-23T18:30'),
  lastUpdated: dayjs('2024-08-23T07:18'),
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
