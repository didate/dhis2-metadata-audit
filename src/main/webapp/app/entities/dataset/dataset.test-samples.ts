import dayjs from 'dayjs/esm';

import { IDataset, NewDataset } from './dataset.model';

export const sampleWithRequiredData: IDataset = {
  id: 'a744bfa7-daa2-47ef-a6c6-92d60cdc065d',
  created: dayjs('2024-08-17T03:12'),
  lastUpdated: dayjs('2024-08-16T23:15'),
  track: 'NEW',
};

export const sampleWithPartialData: IDataset = {
  id: '255550cd-7047-4f07-9eb2-b95208f2c144',
  name: "à l'encontre de cuicui rassurer",
  created: dayjs('2024-08-17T13:41'),
  lastUpdated: dayjs('2024-08-17T08:54'),
  description: 'badaboum',
  mobile: 'interpréter gérer',
  validCompleteOnly: true,
  skipOffline: false,
  compulsoryFieldsCompleteOnly: false,
  formType: 'associer du moment que agréable',
  displayName: 'ronron',
  displayDescription: 'pour oh accroître',
  track: 'UPDATE',
};

export const sampleWithFullData: IDataset = {
  id: '9a24d217-262a-4062-948f-fa68b62e4f6d',
  name: 'tellement',
  created: dayjs('2024-08-17T06:10'),
  lastUpdated: dayjs('2024-08-16T20:01'),
  shortName: 'malgré a',
  description: 'diablement dénoncer de façon à ce que',
  dimensionItemType: 'boire',
  periodType: 'bien que reconnaître aimable',
  mobile: 'triangulaire gigantesque',
  version: 8696.78,
  expiryDays: 17493.35,
  timelyDays: 431.41,
  notifyCompletingUser: 'du moment que vis-à-vie de',
  openFuturePeriods: 18317.96,
  openPeriodsAfterCoEndDate: 14759.1,
  fieldCombinationRequired: true,
  validCompleteOnly: true,
  noValueRequiresComment: true,
  skipOffline: true,
  dataElementDecoration: true,
  renderAsTabs: true,
  renderHorizontally: false,
  compulsoryFieldsCompleteOnly: true,
  formType: 'craquer jusqu’à ce que',
  displayName: 'révéler défaire',
  dimensionItem: 'afin de',
  displayShortName: 'offrir livrer',
  displayDescription: 'près',
  displayFormName: 'cadre devant d’autant que',
  track: 'UPDATE',
};

export const sampleWithNewData: NewDataset = {
  created: dayjs('2024-08-17T03:14'),
  lastUpdated: dayjs('2024-08-16T22:31'),
  track: 'NEW',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
