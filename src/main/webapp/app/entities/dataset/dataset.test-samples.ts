import dayjs from 'dayjs/esm';

import { IDataset, NewDataset } from './dataset.model';

export const sampleWithRequiredData: IDataset = {
  id: '3895f119-a078-4e76-8c61-e1b4ac2d5ec4',
  created: dayjs('2024-08-17T02:44'),
  lastUpdated: dayjs('2024-08-17T09:35'),
};

export const sampleWithPartialData: IDataset = {
  id: 'c4a2bd74-30a3-4983-b3f9-9bbec72a26c5',
  created: dayjs('2024-08-17T09:27'),
  lastUpdated: dayjs('2024-08-17T04:16'),
  description: 'trop quoique',
  dimensionItemType: 'bien que',
  periodType: 'guère mal',
  categoryCombo: 'énorme tenir rédaction',
  openFuturePeriods: 28265.29,
  validCompleteOnly: false,
  dataElementDecoration: true,
  renderHorizontally: true,
  compulsoryFieldsCompleteOnly: true,
  displayShortName: 'avant que direction lentement',
  displayFormName: 'recta',
};

export const sampleWithFullData: IDataset = {
  id: 'bd2ca545-8cc2-4c08-8c6e-fa7ea49602be',
  name: 'dans la mesure où impromptu vu que',
  created: dayjs('2024-08-17T01:19'),
  lastUpdated: dayjs('2024-08-17T12:18'),
  shortName: 'vaste de crainte que',
  description: 'franco reprendre',
  dimensionItemType: 'jeune enfant',
  periodType: 'si cadre dans la mesure où',
  categoryCombo: 'pacifique tant antagoniste',
  mobile: 'nonobstant trop peu',
  version: 514.7,
  expiryDays: 10441.28,
  timelyDays: 3807.65,
  notifyCompletingUser: 'zzzz oh',
  openFuturePeriods: 16254.24,
  openPeriodsAfterCoEndDate: 14096.55,
  fieldCombinationRequired: false,
  validCompleteOnly: true,
  noValueRequiresComment: false,
  skipOffline: false,
  dataElementDecoration: false,
  renderAsTabs: true,
  renderHorizontally: true,
  compulsoryFieldsCompleteOnly: false,
  formType: 'hirsute ouch',
  displayName: 'si',
  dimensionItem: 'commissionnaire',
  displayShortName: 'spécialiste corps enseignant calme',
  displayDescription: 'bè',
  displayFormName: 'rectangulaire deçà à côté de',
};

export const sampleWithNewData: NewDataset = {
  created: dayjs('2024-08-17T10:32'),
  lastUpdated: dayjs('2024-08-17T09:05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
