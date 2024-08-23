import dayjs from 'dayjs/esm';

import { ITrackedEntityAttribute, NewTrackedEntityAttribute } from './tracked-entity-attribute.model';

export const sampleWithRequiredData: ITrackedEntityAttribute = {
  id: 'ccf1a139-b8f3-4cc5-8c03-db8c0eb740c5',
  track: 'UPDATE',
};

export const sampleWithPartialData: ITrackedEntityAttribute = {
  id: '1404fa5d-3bcd-46c2-bafe-3721888cee58',
  created: dayjs('2024-08-22T08:19'),
  name: 'via de manière à ce que rafraîchir',
  generated: true,
  confidential: true,
  displayFormName: 'bien',
  dimensionItemType: 'défiler sur triste',
  aggregationType: 'raide lâche',
  displayName: 'gens atchoum',
  patterne: 'coin-coin admirablement terne',
  displayShortName: 'bang',
  displayOnVisitSchedule: true,
  formName: 'ficher',
  orgunitScope: true,
  dimensionItem: "membre du personnel à l'exception de",
  track: 'UPDATE',
};

export const sampleWithFullData: ITrackedEntityAttribute = {
  id: '34826f93-7dd2-42dc-bd39-fe6480c87ef3',
  lastUpdated: dayjs('2024-08-22T02:27'),
  created: dayjs('2024-08-22T19:12'),
  name: 'coller crac',
  shortName: 'd’autant que quoique tant que',
  generated: false,
  valueType: 'enfin grâce à en outre de',
  confidential: false,
  displayFormName: 'à la faveur de',
  uniquee: false,
  dimensionItemType: 'oups terne',
  aggregationType: 'de façon que',
  displayInListNoProgram: true,
  displayName: 'entre-temps',
  patterne: 'même si en plus de',
  skipSynchronization: true,
  displayShortName: 'tic-tac',
  periodOffset: 1594,
  displayOnVisitSchedule: false,
  formName: 'compromettre adapter en vérité',
  orgunitScope: false,
  dimensionItem: 'oups au-delà',
  inherit: false,
  optionSetValue: false,
  track: 'UPDATE',
};

export const sampleWithNewData: NewTrackedEntityAttribute = {
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
