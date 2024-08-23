import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IDataset, NewDataset } from './dataset.model';

export const sampleWithRequiredData: IDataset = {
  id: '2b755021-e156-42ce-bf07-b8f8cd44aab6',
  created: dayjs('2024-08-22T22:51'),
  lastUpdated: dayjs('2024-08-23T10:40'),
  track: TypeTrack['NEW'],
};

export const sampleWithPartialData: IDataset = {
  id: 'c12e631b-90a9-4ec6-bae3-b41c7d25f30a',
  created: dayjs('2024-08-23T21:30'),
  lastUpdated: dayjs('2024-08-23T11:39'),
  dimensionItemType: 'virtual Sausages',
  version: 65749,
  openFuturePeriods: 14975,
  openPeriodsAfterCoEndDate: 37126,
  fieldCombinationRequired: false,
  noValueRequiresComment: false,
  dataElementDecoration: true,
  renderAsTabs: false,
  renderHorizontally: false,
  compulsoryFieldsCompleteOnly: true,
  displayShortName: 'tan Mozambique payment',
  organisationUnitsCount: 95052,
  track: TypeTrack['NONE'],
};

export const sampleWithFullData: IDataset = {
  id: 'fe471739-ffaf-450b-a390-5de8c96b9693',
  name: 'user',
  created: dayjs('2024-08-23T07:06'),
  lastUpdated: dayjs('2024-08-23T17:43'),
  shortName: 'analyzer index a',
  description: 'copy Steel',
  dimensionItemType: 'Shirt',
  periodType: 'Automated multi-tasking synergistic',
  mobile: 'copying extranet Adaptive',
  version: 75197,
  expiryDays: 76361,
  timelyDays: 69209,
  notifyCompletingUser: 'application orange',
  openFuturePeriods: 77072,
  openPeriodsAfterCoEndDate: 79248,
  fieldCombinationRequired: false,
  validCompleteOnly: false,
  noValueRequiresComment: true,
  skipOffline: true,
  dataElementDecoration: false,
  renderAsTabs: true,
  renderHorizontally: true,
  compulsoryFieldsCompleteOnly: true,
  formType: 'de Corse Saint-Honoré',
  displayName: 'turquoise metrics',
  dimensionItem: 'copy e-business',
  displayShortName: 'Games compress',
  displayDescription: 'Wooden',
  displayFormName: 'bus',
  dataSetElementsCount: 60017,
  indicatorsCount: 40001,
  organisationUnitsCount: 14643,
  dataSetElementsContent: 'mindshare Horizontal',
  indicatorsContent: 'green',
  organisationUnitsContent: 'Cambridgeshire',
  track: TypeTrack['NONE'],
};

export const sampleWithNewData: NewDataset = {
  created: dayjs('2024-08-23T07:55'),
  lastUpdated: dayjs('2024-08-23T21:25'),
  track: TypeTrack['UPDATE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
