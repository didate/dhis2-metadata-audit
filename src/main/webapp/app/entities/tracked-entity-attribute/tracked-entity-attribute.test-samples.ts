import dayjs from 'dayjs/esm';

import { ITrackedEntityAttribute, NewTrackedEntityAttribute } from './tracked-entity-attribute.model';

export const sampleWithRequiredData: ITrackedEntityAttribute = {
  id: '15846750-c991-496b-83c3-78ab4db09796',
  track: 'NEW',
};

export const sampleWithPartialData: ITrackedEntityAttribute = {
  id: '5f61836b-c905-42da-a92e-ee38ad3c18b3',
  lastUpdated: dayjs('2024-08-22T05:29'),
  created: dayjs('2024-08-22T03:07'),
  shortName: 'raccrocher vouh',
  generated: false,
  valueType: 'pourvu que',
  uniquee: false,
  displayOnVisitSchedule: true,
  inherit: true,
  optionSetValue: true,
  track: 'NONE',
};

export const sampleWithFullData: ITrackedEntityAttribute = {
  id: 'de0b11fb-299c-4634-9390-2dd66d76dd5c',
  lastUpdated: dayjs('2024-08-23T00:09'),
  created: dayjs('2024-08-22T14:50'),
  name: 'parce que au-dessous',
  shortName: 'estimer',
  generated: false,
  valueType: 'après que vraisemblablement',
  confidential: false,
  displayFormName: 'bè présidence',
  uniquee: true,
  dimensionItemType: 'de la part de traîner où',
  aggregationType: 'extatique en bas de délégation',
  displayInListNoProgram: true,
  displayName: 'moins cuicui membre à vie',
  patterne: 'pendant que gens vorace',
  skipSynchronization: false,
  displayShortName: 'sentir ding quoique',
  periodOffset: 30293,
  displayOnVisitSchedule: true,
  formName: 'vroum rectorat',
  orgunitScope: false,
  dimensionItem: 'aux alentours de à la merci pourvu que',
  inherit: true,
  optionSetValue: true,
  track: 'NEW',
};

export const sampleWithNewData: NewTrackedEntityAttribute = {
  track: 'NEW',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
