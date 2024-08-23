import { ICategorycombo, NewCategorycombo } from './categorycombo.model';

export const sampleWithRequiredData: ICategorycombo = {
  id: '9246f1a8-bc1b-4b7f-8a0f-bc1e8673b0a1',
};

export const sampleWithPartialData: ICategorycombo = {
  id: '98741fe2-ad1e-4fd4-aba0-b6eaf209f44b',
  name: 'novice',
};

export const sampleWithFullData: ICategorycombo = {
  id: '6096c3e4-a387-4d29-a5c5-65d3411234e2',
  name: "d'après",
};

export const sampleWithNewData: NewCategorycombo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
