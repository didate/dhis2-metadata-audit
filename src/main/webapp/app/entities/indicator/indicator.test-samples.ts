import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IIndicator, NewIndicator } from './indicator.model';

export const sampleWithRequiredData: IIndicator = {
  id: '2b11420e-cb4b-44b0-b259-70070a9029c6',
  name: 'Kuna bluetooth Koruna',
  shortName: 'Savings Plastic',
  created: dayjs('2024-08-22T22:21'),
  lastUpdated: dayjs('2024-08-23T04:49'),
  publicAccess: 'invoice Malaisie',
  dimensionItemType: 'Licensed Indonésie tchèque',
  annualized: true,
  track: TypeTrack['NEW'],
};

export const sampleWithPartialData: IIndicator = {
  id: '5256c6d8-3c1a-4c88-8ded-6c11018eb76f',
  name: 'optimize functionalities',
  shortName: 'application',
  created: dayjs('2024-08-23T01:37'),
  lastUpdated: dayjs('2024-08-23T18:52'),
  publicAccess: 'blue payment Cotton',
  dimensionItemType: 'invoice capacitor',
  annualized: false,
  denominator: 'firewall concept',
  denominatorDescription: 'Fantastic Lepic Realigned',
  displayNumeratorDescription: 'Fantastic',
  displayDenominatorDescription: 'b',
  track: TypeTrack['UPDATE'],
};

export const sampleWithFullData: IIndicator = {
  id: '57174d87-b7c6-4c76-b85d-d0d3ef621cfa',
  name: 'microchip adapter Account',
  shortName: 'Plastic methodology',
  displayShortName: 'invoice',
  displayName: 'SCSI violet Cambridgeshire',
  displayFormName: 'copy wireless',
  created: dayjs('2024-08-23T07:38'),
  lastUpdated: dayjs('2024-08-23T09:37'),
  publicAccess: 'Stand-alone',
  dimensionItemType: 'gold',
  annualized: false,
  numerator: '1080p',
  numeratorDescription: 'Rustic',
  denominator: 'cyan enable',
  denominatorDescription: 'scale',
  displayNumeratorDescription: 'Centralized bypassing Savings',
  displayDenominatorDescription: 'Franc partnerships',
  dimensionItem: 'a channels Home',
  track: TypeTrack['UPDATE'],
};

export const sampleWithNewData: NewIndicator = {
  name: 'Analyste Beauty Automotive',
  shortName: 'pixel Presbourg',
  created: dayjs('2024-08-22T23:02'),
  lastUpdated: dayjs('2024-08-23T16:33'),
  publicAccess: 'Bonaparte Directeur',
  dimensionItemType: 'Outdoors',
  annualized: true,
  track: TypeTrack['NONE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
