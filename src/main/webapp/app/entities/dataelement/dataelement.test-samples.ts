import dayjs from 'dayjs/esm';

import { IDataelement, NewDataelement } from './dataelement.model';

export const sampleWithRequiredData: IDataelement = {
  id: '08d4036d-6966-41b9-b297-eec1b45eaec3',
  name: 'tellement',
  shortName: 'volontiers',
  created: dayjs('2024-08-17T03:56'),
  lastUpdated: dayjs('2024-08-17T00:24'),
  publicAccess: 'pour que sauf à comprendre',
  dimensionItemType: 'à condition que croâ',
  aggregationType: 'demain membre de l’équipe secours',
  valueType: 'rédaction paraître',
  domainType: 'bè spécialiste pater',
};

export const sampleWithPartialData: IDataelement = {
  id: 'fd6c6d0e-56dd-4f2a-8fe6-78829d8297ad',
  name: 'simple trop avant de',
  shortName: 'tchou tchouu conseil municipal ensemble',
  formName: 'précipiter membre de l’équipe tsoin-tsoin',
  description: 'depuis tic-tac',
  displayName: 'pourvu que',
  displayFormName: 'après que hi rectorat',
  created: dayjs('2024-08-17T13:15'),
  lastUpdated: dayjs('2024-08-17T02:51'),
  publicAccess: 'personnel au-dessus pendant que',
  dimensionItemType: 'grâce à',
  aggregationType: 'sympathique malade',
  valueType: 'touchant souple',
  domainType: 'aussitôt que assurément',
};

export const sampleWithFullData: IDataelement = {
  id: '8b0089db-ba93-4ce0-8d6f-8963291ee4a4',
  name: 'ressusciter considérable hors',
  shortName: 'au lieu de',
  formName: 'de façon que tandis que',
  description: 'pour que',
  displayShortName: 'dring',
  displayName: 'groin groin',
  displayFormName: 'tsoin-tsoin à la merci',
  created: dayjs('2024-08-17T14:07'),
  lastUpdated: dayjs('2024-08-17T00:52'),
  publicAccess: 'euh pff',
  dimensionItemType: 'transformer',
  aggregationType: 'pff',
  valueType: 'à la faveur de absorber abandonner',
  domainType: "agréable à l'instar de prout",
  zeroIsSignificant: false,
  optionSetValue: 'attacher biathlète cocorico',
  dimensionItem: 'tuer',
};

export const sampleWithNewData: NewDataelement = {
  name: 'debout',
  shortName: 'ôter',
  created: dayjs('2024-08-17T10:46'),
  lastUpdated: dayjs('2024-08-17T14:14'),
  publicAccess: 'lorsque pour que à condition que',
  dimensionItemType: 'population du Québec',
  aggregationType: 'plic sitôt que fade',
  valueType: 'adepte',
  domainType: 'vu que',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
