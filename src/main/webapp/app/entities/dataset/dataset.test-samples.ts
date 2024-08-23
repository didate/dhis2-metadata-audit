import dayjs from 'dayjs/esm';

import { IDataset, NewDataset } from './dataset.model';

export const sampleWithRequiredData: IDataset = {
  id: '2c4182df-24ac-47f0-9b47-c5078a00b726',
  created: dayjs('2024-08-17T15:03'),
  lastUpdated: dayjs('2024-08-17T09:47'),
  track: 'NONE',
};

export const sampleWithPartialData: IDataset = {
  id: '3fa3a3c1-632d-4647-a474-df4ed5a228cf',
  name: 'hors de splendide pourrir',
  created: dayjs('2024-08-17T01:02'),
  lastUpdated: dayjs('2024-08-17T11:06'),
  shortName: 'alors renforcer',
  description: 'circulaire de peur que de peur que',
  dimensionItemType: 'euh d’autant que que',
  notifyCompletingUser: 'tchou tchouu',
  openFuturePeriods: 26107.56,
  openPeriodsAfterCoEndDate: 18852.39,
  fieldCombinationRequired: true,
  noValueRequiresComment: true,
  skipOffline: true,
  renderAsTabs: false,
  dimensionItem: 'fournir alors que pour que',
  displayShortName: 'à côté de',
  displayFormName: 'tranquille sous au cas où',
  dataSetElementsCount: 3901,
  indicatorsCount: 23228,
  dataSetElementsContent: 'trop près de façon à ce que',
  indicatorsContent: 'équipe de recherche magenta accepter',
  track: 'NONE',
};

export const sampleWithFullData: IDataset = {
  id: 'ae1b95f0-a3bf-48b4-890c-95f979c19c6c',
  name: 'boum athlète',
  created: dayjs('2024-08-17T05:19'),
  lastUpdated: dayjs('2024-08-17T10:59'),
  shortName: 'beaucoup bang',
  description: 'pas mal',
  dimensionItemType: 'extrêmement',
  periodType: 'fourbe de',
  mobile: 'coupable entre-temps',
  version: 29737.37,
  expiryDays: 8080.5,
  timelyDays: 29578.92,
  notifyCompletingUser: "à l'instar de vu que",
  openFuturePeriods: 11343.44,
  openPeriodsAfterCoEndDate: 31241.19,
  fieldCombinationRequired: true,
  validCompleteOnly: false,
  noValueRequiresComment: false,
  skipOffline: true,
  dataElementDecoration: true,
  renderAsTabs: false,
  renderHorizontally: false,
  compulsoryFieldsCompleteOnly: false,
  formType: 'quand infiniment sympathique',
  displayName: 'sitôt que âcre',
  dimensionItem: 'venir exploiter magnifique',
  displayShortName: 'au-dessus',
  displayDescription: 'chut',
  displayFormName: 'derrière adapter quand',
  dataSetElementsCount: 21906,
  indicatorsCount: 17417,
  organisationUnitsCount: 24240,
  dataSetElementsContent: 'chez',
  indicatorsContent: 'chut lorsque assez',
  organisationUnitsContent: 'ferme déduire même si',
  track: 'NONE',
};

export const sampleWithNewData: NewDataset = {
  created: dayjs('2024-08-17T06:15'),
  lastUpdated: dayjs('2024-08-17T12:47'),
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
