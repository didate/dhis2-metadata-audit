import { ICategorycombo, NewCategorycombo } from './categorycombo.model';

export const sampleWithRequiredData: ICategorycombo = {
  id: '2c6d059a-e356-4e3e-a2ed-f08ccf9e75d9',
};

export const sampleWithPartialData: ICategorycombo = {
  id: '01bbd5bd-00e3-44ca-b004-0a9808f21151',
};

export const sampleWithFullData: ICategorycombo = {
  id: 'a0845fad-5a47-459d-93fc-07f10b6f85c5',
  name: 'miaou touriste interpréter',
};

export const sampleWithNewData: NewCategorycombo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
