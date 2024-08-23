import { IOptionGroup, NewOptionGroup } from './option-group.model';

export const sampleWithRequiredData: IOptionGroup = {
  id: '9717365d-7211-48f2-ab33-30e71a00fb00',
};

export const sampleWithPartialData: IOptionGroup = {
  id: '3876ab11-ae4d-4e00-b6de-e302df65bd46',
};

export const sampleWithFullData: IOptionGroup = {
  id: '1c07dbb5-5b80-4736-a3e6-227068723b1f',
};

export const sampleWithNewData: NewOptionGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
