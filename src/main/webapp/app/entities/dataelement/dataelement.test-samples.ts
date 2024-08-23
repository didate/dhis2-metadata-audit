import dayjs from 'dayjs/esm';

import { IDataelement, NewDataelement } from './dataelement.model';

export const sampleWithRequiredData: IDataelement = {
  id: '2599babc-0d06-4e85-adb1-89eaf7c347d2',
  name: 'contempler rapprocher main-d’œuvre',
  shortName: 'au cas où',
  created: dayjs('2024-08-16T19:28'),
  lastUpdated: dayjs('2024-08-16T22:39'),
  publicAccess: 'en dehors de pff serviable',
  dimensionItemType: 'alors circulaire',
  aggregationType: 'quand ? très',
  valueType: 'devant collègue clientèle',
  domainType: 'électorat diablement',
  track: 'NONE',
};

export const sampleWithPartialData: IDataelement = {
  id: '4af0c5d2-4934-40ac-80fd-ba2a9171452d',
  name: 'croâ',
  shortName: "ailleurs d'entre",
  formName: 'jusqu’à ce que blême jamais',
  description: 'diplomate dorénavant administration',
  created: dayjs('2024-08-17T00:37'),
  lastUpdated: dayjs('2024-08-17T14:45'),
  publicAccess: 'pin-pon',
  dimensionItemType: 'croâ oh personnel professionnel',
  aggregationType: 'à peine tordre',
  valueType: 'lasser au-dedans de parfois',
  domainType: 'mentionner lâche',
  optionSetValue: 'pendant',
  track: 'NEW',
};

export const sampleWithFullData: IDataelement = {
  id: '03847f80-abdc-434a-b930-4aa559b3c474',
  name: 'mieux renier sincère',
  shortName: 'puisque',
  formName: 'bzzz extra',
  description: 'électorat',
  displayShortName: 'immense quand quand',
  displayName: 'via',
  displayFormName: 'de façon à ce que pschitt',
  created: dayjs('2024-08-17T06:51'),
  lastUpdated: dayjs('2024-08-16T22:34'),
  publicAccess: 'hormis du fait que',
  dimensionItemType: 'quant à',
  aggregationType: 'avant que de façon à ce que',
  valueType: 'vraisemblablement',
  domainType: 'gratis',
  zeroIsSignificant: true,
  optionSetValue: 'psitt',
  dimensionItem: 'chut en faveur de respirer',
  track: 'NEW',
};

export const sampleWithNewData: NewDataelement = {
  name: 'refuser moyennant puisque',
  shortName: 'brusque délégation',
  created: dayjs('2024-08-17T13:43'),
  lastUpdated: dayjs('2024-08-17T15:05'),
  publicAccess: 'avant',
  dimensionItemType: 'sombre triste',
  aggregationType: 'au-dessus de spécialiste reprocher',
  valueType: 'brave',
  domainType: 'corps enseignant',
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
