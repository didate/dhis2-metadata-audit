import dayjs from 'dayjs/esm';

import { IIndicator, NewIndicator } from './indicator.model';

export const sampleWithRequiredData: IIndicator = {
  id: 'ed527686-57ac-447f-8dee-153727aade3f',
  name: 'fonctionnaire dans',
  shortName: 'solitaire espiègle',
  created: dayjs('2024-08-17T11:54'),
  lastUpdated: dayjs('2024-08-17T10:06'),
  publicAccess: 'plic',
  dimensionItemType: 'ouf miam',
  annualized: true,
  track: 'NONE',
};

export const sampleWithPartialData: IIndicator = {
  id: '69a6279d-7379-493e-b221-60e119d222f6',
  name: 'ensuite large',
  shortName: 'ça',
  displayName: 'cependant',
  created: dayjs('2024-08-17T01:25'),
  lastUpdated: dayjs('2024-08-16T16:22'),
  publicAccess: 'crac trop à travers',
  dimensionItemType: 'personnel',
  annualized: true,
  numerator: 'sombre',
  denominator: 'chef de cuisine contre',
  dimensionItem: 'taire alors que',
  track: 'NONE',
};

export const sampleWithFullData: IIndicator = {
  id: '442d2b66-d4bf-4d2a-a168-aab7335c7c64',
  name: 'ouah',
  shortName: 'souvent',
  displayShortName: 'ouin',
  displayName: 'membre de l’équipe traiter pacifique',
  displayFormName: 'outre céder',
  created: dayjs('2024-08-17T03:33'),
  lastUpdated: dayjs('2024-08-17T04:18'),
  publicAccess: 'blesser',
  dimensionItemType: 'ha ha',
  annualized: true,
  numerator: 'souffrir',
  numeratorDescription: 'bof',
  denominator: 'de sorte que interdire',
  denominatorDescription: 'franco',
  displayNumeratorDescription: 'chut',
  displayDenominatorDescription: 'carrément',
  dimensionItem: 'durant confondre beaucoup',
  track: 'NEW',
};

export const sampleWithNewData: NewIndicator = {
  name: 'joliment hôte',
  shortName: 'pour que miam ouin',
  created: dayjs('2024-08-16T22:56'),
  lastUpdated: dayjs('2024-08-16T18:40'),
  publicAccess: 'arrière',
  dimensionItemType: 'orange pardonner',
  annualized: false,
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
