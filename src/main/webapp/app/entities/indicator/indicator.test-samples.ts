import dayjs from 'dayjs/esm';

import { IIndicator, NewIndicator } from './indicator.model';

export const sampleWithRequiredData: IIndicator = {
  id: '3207a05f-4a2e-4dba-b154-cea5130ce379',
  name: 'diminuer',
  shortName: 'solitaire que ronron',
  created: dayjs('2024-08-17T04:03'),
  lastUpdated: dayjs('2024-08-17T12:44'),
  publicAccess: 'de façon à ce que ah police',
  dimensionItemType: 'oh au point que sans doute',
  annualized: false,
  track: 'NONE',
};

export const sampleWithPartialData: IIndicator = {
  id: 'd1555717-0cc4-4fd3-968a-4b4b546039ed',
  name: 'avex là de façon à ce que',
  shortName: 'comme',
  displayShortName: 'tantôt de manière à',
  displayName: 'crac rose',
  displayFormName: 'aimable fonctionnaire dense',
  created: dayjs('2024-08-17T00:27'),
  lastUpdated: dayjs('2024-08-17T08:09'),
  publicAccess: 'grouper',
  dimensionItemType: 'avant que bientôt membre à vie',
  annualized: true,
  numerator: 'du moment que trier',
  denominator: 'enregistrer',
  displayNumeratorDescription: 'jaillir entre',
  track: 'NEW',
};

export const sampleWithFullData: IIndicator = {
  id: '59663658-2fa3-4844-8da8-c521a076a7cd',
  name: 'disputer intrépide clientèle',
  shortName: 'juriste assez résigner',
  displayShortName: 'fort fréquenter rédaction',
  displayName: 'près de pour que vlan',
  displayFormName: 'demain',
  created: dayjs('2024-08-17T00:03'),
  lastUpdated: dayjs('2024-08-17T00:58'),
  publicAccess: 'au cas où vis-à-vie de',
  dimensionItemType: 'enfoncer passablement',
  annualized: true,
  numerator: 'suivant',
  numeratorDescription: 'coupable user',
  denominator: 'commissionnaire glouglou',
  denominatorDescription: 'lorsque introduire',
  displayNumeratorDescription: 'espiègle snif',
  displayDenominatorDescription: 'marier à même',
  dimensionItem: 'puis',
  track: 'NEW',
};

export const sampleWithNewData: NewIndicator = {
  name: 'proche de',
  shortName: 'ouch',
  created: dayjs('2024-08-17T14:26'),
  lastUpdated: dayjs('2024-08-17T05:54'),
  publicAccess: "vlan à l'entour de fade",
  dimensionItemType: 'mal direction',
  annualized: true,
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
