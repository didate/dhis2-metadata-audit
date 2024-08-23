import { IOptionGroup, NewOptionGroup } from './option-group.model';

export const sampleWithRequiredData: IOptionGroup = {
  id: '55645064-88ba-4e9c-af20-d5a9a8e17070',
};

export const sampleWithPartialData: IOptionGroup = {
  id: 'a2ccca92-d76b-4ef9-b175-373228490ec9',
};

export const sampleWithFullData: IOptionGroup = {
  id: 'ed1d9210-27a0-441c-9e44-df3bd0949710',
};

export const sampleWithNewData: NewOptionGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
