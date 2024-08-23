import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IProgramIndicator, NewProgramIndicator } from './program-indicator.model';

export const sampleWithRequiredData: IProgramIndicator = {
  id: '194278d2-370b-4158-9c48-98df303c0903',
  name: 'hybrid killer',
  created: dayjs('2024-08-23T19:45'),
  lastUpdated: dayjs('2024-08-22T23:45'),
  track: TypeTrack['UPDATE'],
};

export const sampleWithPartialData: IProgramIndicator = {
  id: '3e12cc8e-d862-426d-9f0f-7a7ddde7bc5c',
  name: 'a Bedfordshire',
  created: dayjs('2024-08-23T06:28'),
  lastUpdated: dayjs('2024-08-23T05:58'),
  expression: 'Account bandwidth',
  displayShortName: 'Andorre',
  displayFormName: 'Dirham Ameliorated',
  track: TypeTrack['NEW'],
};

export const sampleWithFullData: IProgramIndicator = {
  id: 'd72878a9-c51a-4c89-ae96-b4d612969ce8',
  name: 'relationships olive panel',
  created: dayjs('2024-08-23T14:07'),
  lastUpdated: dayjs('2024-08-23T06:21'),
  shortName: 'redundant',
  dimensionItemType: 'override Movies',
  expression: 'Haute-Normandie online',
  filter: 'b optimize',
  analyticsType: 'Phased backing infomediaries',
  dimensionItem: 'Buckinghamshire Cambridgeshire',
  displayShortName: 'JSON Licensed compress',
  displayName: 'Fish',
  displayFormName: 'productize plug-and-play e-tailers',
  track: TypeTrack['NONE'],
};

export const sampleWithNewData: NewProgramIndicator = {
  name: 'infrastructures standardization compressing',
  created: dayjs('2024-08-23T16:29'),
  lastUpdated: dayjs('2024-08-23T04:52'),
  track: TypeTrack['NONE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
