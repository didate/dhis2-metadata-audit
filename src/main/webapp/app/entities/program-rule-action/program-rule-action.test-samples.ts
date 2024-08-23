import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IProgramRuleAction, NewProgramRuleAction } from './program-rule-action.model';

export const sampleWithRequiredData: IProgramRuleAction = {
  id: 'ff38c5aa-d470-4392-947f-7159c3681b59',
  track: TypeTrack['NEW'],
};

export const sampleWithPartialData: IProgramRuleAction = {
  id: 'c2945dbb-ed90-4c0e-b9bf-6ae1e07127d7',
  programRuleActionType: 'Ghana',
  data: 'Nicaragua',
  templateUid: 'Bike infrastructure',
  content: 'Ukraine Horizontal',
  track: TypeTrack['NONE'],
};

export const sampleWithFullData: IProgramRuleAction = {
  id: 'e0e4d3c2-775a-4403-860b-20d780226034',
  lastUpdated: dayjs('2024-08-23T11:48'),
  created: 'Buckinghamshire Open-architected Outdoors',
  programRuleActionType: 'Palladium',
  evaluationTime: 'Soap Centre a',
  data: 'Sum',
  templateUid: 'b solid',
  content: 'Directeur',
  displayContent: 'seamless',
  track: TypeTrack['NONE'],
};

export const sampleWithNewData: NewProgramRuleAction = {
  track: TypeTrack['NEW'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
