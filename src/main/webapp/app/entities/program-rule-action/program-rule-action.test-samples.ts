import dayjs from 'dayjs/esm';

import { IProgramRuleAction, NewProgramRuleAction } from './program-rule-action.model';

export const sampleWithRequiredData: IProgramRuleAction = {
  id: '04c3269c-0dde-44de-a8c2-0c6f0f2d5f09',
  track: 'NEW',
};

export const sampleWithPartialData: IProgramRuleAction = {
  id: 'b9ce44ee-3e43-44f0-ba97-c95144a83264',
  templateUid: 'assez hi exercer',
  content: 'aimable si',
  track: 'UPDATE',
};

export const sampleWithFullData: IProgramRuleAction = {
  id: 'd8a1ae5e-43fb-41f3-ab9c-b4af876d740c',
  lastUpdated: dayjs('2024-08-22T21:02'),
  created: 'après que',
  programRuleActionType: 'spécialiste de façon à de',
  evaluationTime: 'à la merci',
  data: 'sacrifier',
  templateUid: 'différer',
  content: 'débile prout',
  displayContent: 'raide partenaire tellement',
  track: 'UPDATE',
};

export const sampleWithNewData: NewProgramRuleAction = {
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
