import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 24393,
  login: 'dnY@lq',
};

export const sampleWithPartialData: IUser = {
  id: 28248,
  login: '5X',
};

export const sampleWithFullData: IUser = {
  id: 9226,
  login: 's',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
