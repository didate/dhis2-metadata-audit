import { ICategorycombo, NewCategorycombo } from './categorycombo.model';

export const sampleWithRequiredData: ICategorycombo = {
  id: 'c38848e4-6fbe-4d86-b842-0e157b380b3e',
};

export const sampleWithPartialData: ICategorycombo = {
  id: '556cad37-84fd-4e44-b7b6-9060f67fca6f',
};

export const sampleWithFullData: ICategorycombo = {
  id: '7bdf0eb2-7ea3-40a3-82b9-5ed9f182b2d8',
  name: 'approximativement essayer',
};

export const sampleWithNewData: NewCategorycombo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
