import { ICategorycombo, NewCategorycombo } from './categorycombo.model';

export const sampleWithRequiredData: ICategorycombo = {
  id: '918ac1e9-cb62-467e-84c6-533cd04ae21b',
};

export const sampleWithPartialData: ICategorycombo = {
  id: '21caee35-0a38-450a-b064-fcd25da4e363',
};

export const sampleWithFullData: ICategorycombo = {
  id: '692aba26-9c8f-48c1-9df4-a18cec4cf6e3',
  name: 'Pizza',
};

export const sampleWithNewData: NewCategorycombo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
