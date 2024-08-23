import dayjs from 'dayjs/esm';

import { IDataelement, NewDataelement } from './dataelement.model';

export const sampleWithRequiredData: IDataelement = {
  id: '67b9fe8c-3c48-43b8-8cec-8d5cb5d0f9a9',
  name: 'secouriste',
  shortName: 'plic carrément',
  created: dayjs('2024-08-17T05:57'),
  lastUpdated: dayjs('2024-08-16T17:11'),
  publicAccess: 'débattre',
  dimensionItemType: 'cot cot amorphe',
  aggregationType: 'intrépide touchant',
  valueType: 'dès',
  domainType: 'attacher membre titulaire',
  track: 'UPDATE',
};

export const sampleWithPartialData: IDataelement = {
  id: 'b8415769-2609-4f60-8947-3962e9d7194a',
  name: 'à raison de rudement délectable',
  shortName: 'placide',
  formName: 'coordonner au dépens de',
  displayShortName: 'infiniment',
  displayName: 'aussi hormis',
  created: dayjs('2024-08-16T22:28'),
  lastUpdated: dayjs('2024-08-16T22:31'),
  publicAccess: 'en outre de sans',
  dimensionItemType: 'lunatique spécialiste clac',
  aggregationType: 'aïe quand sage',
  valueType: 'autour débile de peur que',
  domainType: 'pisser',
  dimensionItem: 'bof atchoum à demi',
  track: 'NEW',
};

export const sampleWithFullData: IDataelement = {
  id: '9763d42a-9018-4acc-a0f7-96ff20c90316',
  name: 'toutefois',
  shortName: 'aborder si',
  formName: 'jamais également suivant',
  description: 'tandis que autrefois',
  displayShortName: 'diablement',
  displayName: 'soudain avant de',
  displayFormName: 'tard',
  created: dayjs('2024-08-17T09:26'),
  lastUpdated: dayjs('2024-08-17T10:52'),
  publicAccess: 'prendre là',
  dimensionItemType: 'jeune enfant',
  aggregationType: 'projeter quoique au point que',
  valueType: 'hi de façon à accepter',
  domainType: 'glouglou premièrement incalculable',
  zeroIsSignificant: true,
  optionSetValue: 'concurrence',
  dimensionItem: 'blême au-dessus extra',
  track: 'NEW',
};

export const sampleWithNewData: NewDataelement = {
  name: 'ça au-delà triangulaire',
  shortName: 'accommoder en decà de considérable',
  created: dayjs('2024-08-16T19:06'),
  lastUpdated: dayjs('2024-08-16T19:19'),
  publicAccess: 'incognito',
  dimensionItemType: 'du fait que',
  aggregationType: 'lorsque ficher',
  valueType: 'approximativement',
  domainType: 'approcher de crainte que de sorte que',
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
