import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { ITrackedEntityAttribute, NewTrackedEntityAttribute } from './tracked-entity-attribute.model';

export const sampleWithRequiredData: ITrackedEntityAttribute = {
  id: '37b1f49c-fc0d-4e58-86f2-3a1174fea80a',
  track: TypeTrack['NONE'],
};

export const sampleWithPartialData: ITrackedEntityAttribute = {
  id: '817b6054-f414-4d08-86fa-33f9bd49140a',
  lastUpdated: dayjs('2024-08-23T20:25'),
  confidential: true,
  uniquee: true,
  dimensionItemType: 'extend virtual',
  aggregationType: 'web-enabled Health Aquitaine',
  displayInListNoProgram: false,
  displayShortName: 'Metal application virtual',
  periodOffset: 80760,
  displayOnVisitSchedule: true,
  orgunitScope: false,
  dimensionItem: 'metrics',
  track: TypeTrack['NONE'],
};

export const sampleWithFullData: ITrackedEntityAttribute = {
  id: '3df25f2e-9332-4443-a089-d1786ac3ca1b',
  lastUpdated: dayjs('2024-08-23T10:06'),
  created: dayjs('2024-08-23T20:31'),
  name: 'Concrete digital Consultant',
  shortName: 'engage b',
  generated: true,
  valueType: 'Loan Buckinghamshire Hat',
  confidential: true,
  displayFormName: 'TCP Saint-Bernard',
  uniquee: true,
  dimensionItemType: 'white neural',
  aggregationType: 'Shoes intranet',
  displayInListNoProgram: true,
  displayName: 'Borders',
  patterne: 'connect Multi-layered parsing',
  skipSynchronization: false,
  displayShortName: 'Manager a',
  periodOffset: 23256,
  displayOnVisitSchedule: false,
  formName: 'synthesizing RAM foreground',
  orgunitScope: false,
  dimensionItem: 'Berkshire feed',
  inherit: true,
  optionSetValue: true,
  track: TypeTrack['NEW'],
};

export const sampleWithNewData: NewTrackedEntityAttribute = {
  track: TypeTrack['NONE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
