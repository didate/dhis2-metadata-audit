import { IOptionset, NewOptionset } from './optionset.model';

export const sampleWithRequiredData: IOptionset = {
  id: '97b2e501-c8e0-43f0-90fb-3a5bb32106cf',
};

export const sampleWithPartialData: IOptionset = {
  id: '734e78c5-1d7a-48a6-83d3-c4f3902a917c',
  name: 'abuser',
};

export const sampleWithFullData: IOptionset = {
  id: '4ccff7ab-7cfa-46d8-85c8-77927dc275e1',
  name: 'juriste tromper via',
};

export const sampleWithNewData: NewOptionset = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
