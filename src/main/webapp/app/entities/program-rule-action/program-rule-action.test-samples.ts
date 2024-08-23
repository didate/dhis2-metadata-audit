import dayjs from 'dayjs/esm';

import { IProgramRuleAction, NewProgramRuleAction } from './program-rule-action.model';

export const sampleWithRequiredData: IProgramRuleAction = {
  id: '62b49497-6af4-40d0-9f80-0d8dc546ecdd',
  track: 'NONE',
};

export const sampleWithPartialData: IProgramRuleAction = {
  id: 'd2637f3f-6f1f-4a05-8a41-6bee6d415c7b',
  created: 'rectorat jusqu’à ce que',
  evaluationTime: 'pressentir super sédentaire',
  data: 'secouriste vaste là-haut',
  templateUid: 'durant',
  track: 'UPDATE',
};

export const sampleWithFullData: IProgramRuleAction = {
  id: '48d4eba4-f99a-4bfc-a0c1-cabe2051c8b1',
  lastUpdated: dayjs('2024-08-22T10:49'),
  created: 'pourvu que chef de cuisine fort',
  programRuleActionType: 'badaboum',
  evaluationTime: 'quand en outre de',
  data: 'repérer puisque',
  templateUid: 'coin-coin après un peu',
  content: 'en guise de chef autour de',
  displayContent: 'sans que',
  track: 'UPDATE',
};

export const sampleWithNewData: NewProgramRuleAction = {
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
