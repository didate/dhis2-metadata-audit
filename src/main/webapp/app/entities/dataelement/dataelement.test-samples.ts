import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IDataelement, NewDataelement } from './dataelement.model';

export const sampleWithRequiredData: IDataelement = {
  id: '3e06775f-8e2d-4985-8cde-373885730980',
  name: 'Loan',
  shortName: 'EXE',
  created: dayjs('2024-08-22T22:24'),
  lastUpdated: dayjs('2024-08-23T09:52'),
  publicAccess: 'Congo hack open-source',
  dimensionItemType: 'hack Loire Awesome',
  aggregationType: 'bypassing cross-platform',
  valueType: 'Tasty niches',
  domainType: 'implement',
  track: TypeTrack['NONE'],
};

export const sampleWithPartialData: IDataelement = {
  id: '08e4e330-6c92-46b3-9550-95e1fdde722c',
  name: 'SSL Laos',
  shortName: 'markets Automotive aggregate',
  formName: 'support b',
  description: 'Midi-Pyrénées synergize',
  displayFormName: 'Consultant a Upgradable',
  created: dayjs('2024-08-23T10:40'),
  lastUpdated: dayjs('2024-08-23T11:58'),
  publicAccess: 'Huchette indigo',
  dimensionItemType: 'Monsieur-le-Prince middleware',
  aggregationType: 'repurpose',
  valueType: 'program',
  domainType: 'Wooden',
  dimensionItem: 'up Cotton',
  track: TypeTrack['NONE'],
};

export const sampleWithFullData: IDataelement = {
  id: 'f345df71-83b0-4061-aaa5-5533f2f320a3',
  name: 'Ingenieur website c',
  shortName: 'Loan overriding',
  formName: 'maroon',
  description: 'b SAS Iran',
  displayShortName: 'firewall a payment',
  displayName: 'Licensed Baby',
  displayFormName: 'yellow Automotive',
  created: dayjs('2024-08-23T00:11'),
  lastUpdated: dayjs('2024-08-23T15:56'),
  publicAccess: 'e-commerce',
  dimensionItemType: 'Account User-friendly withdrawal',
  aggregationType: 'Soap Money',
  valueType: 'AGP back',
  domainType: 'parsing Tuna Investment',
  zeroIsSignificant: false,
  optionSetValue: 'c Agent',
  dimensionItem: 'website a a',
  track: TypeTrack['NONE'],
};

export const sampleWithNewData: NewDataelement = {
  name: 'protocol vortals orchid',
  shortName: 'Franche-Comté robust Granite',
  created: dayjs('2024-08-23T02:52'),
  lastUpdated: dayjs('2024-08-23T04:43'),
  publicAccess: 'wireless deliverables',
  dimensionItemType: 'XSS Developpeur b',
  aggregationType: 'transmitting',
  valueType: 'Languedoc-Roussillon a Handmade',
  domainType: 'c b synergies',
  track: TypeTrack['NEW'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
