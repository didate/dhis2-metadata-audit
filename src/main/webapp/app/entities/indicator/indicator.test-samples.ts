import dayjs from 'dayjs/esm';

import { IIndicator, NewIndicator } from './indicator.model';

export const sampleWithRequiredData: IIndicator = {
  id: '70fc5a56-89e0-4a0f-b569-d5ee1a0fad9d',
  name: 'candide',
  shortName: 'à peu près rédaction sous couleur de',
  created: dayjs('2024-08-17T15:09'),
  lastUpdated: dayjs('2024-08-16T21:15'),
  publicAccess: 'de',
  dimensionItemType: 'de sorte que lâche',
  annualized: false,
};

export const sampleWithPartialData: IIndicator = {
  id: '6a6ec237-03e2-44f6-b4d8-8ec8f9110068',
  name: 'parce que plaisanter',
  shortName: 'dégager au cas où communauté étudiante',
  created: dayjs('2024-08-17T07:58'),
  lastUpdated: dayjs('2024-08-17T10:27'),
  publicAccess: 'géométrique',
  dimensionItemType: 'ha ça à condition que',
  annualized: true,
  numeratorDescription: 'rudement pourvu que lunatique',
  denominator: 'bè pleuvoir candide',
  denominatorDescription: 'transporter triste insolite',
  displayNumeratorDescription: 'à raison de',
};

export const sampleWithFullData: IIndicator = {
  id: '5741d025-8052-4524-917e-fc3f1c9b31ee',
  name: 'de façon à ce que',
  shortName: 'après énorme',
  displayShortName: 'touriste différer que',
  displayName: 'toc vorace échanger',
  displayFormName: 'prestataire de services si bien que',
  created: dayjs('2024-08-16T19:16'),
  lastUpdated: dayjs('2024-08-16T19:51'),
  publicAccess: 'regretter au-dessous',
  dimensionItemType: 'manger rafraîchir de façon que',
  annualized: true,
  numerator: 'poser promettre',
  numeratorDescription: 'adepte tomber secours',
  denominator: 'en vérité près',
  denominatorDescription: 'trop',
  displayNumeratorDescription: 'orange de par',
  displayDenominatorDescription: 'bzzz',
  dimensionItem: 'par placide loin de',
};

export const sampleWithNewData: NewIndicator = {
  name: 'juriste à partir de',
  shortName: 'confronter relire',
  created: dayjs('2024-08-17T03:21'),
  lastUpdated: dayjs('2024-08-16T20:46'),
  publicAccess: 'brave également commis de cuisine',
  dimensionItemType: 'pourvu que drelin',
  annualized: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
