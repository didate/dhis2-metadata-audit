import { IOptionGroup, NewOptionGroup } from './option-group.model';

export const sampleWithRequiredData: IOptionGroup = {
  id: '2e48bdbf-ed18-4894-aae8-a58a9e32a089',
};

export const sampleWithPartialData: IOptionGroup = {
  id: '196db80a-c2ea-421f-abc3-0cd2c63bb0ff',
};

export const sampleWithFullData: IOptionGroup = {
  id: '1faf7d1d-db11-4145-9bbb-4bc74b7355a8',
};

export const sampleWithNewData: NewOptionGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
