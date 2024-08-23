import { IOptionset, NewOptionset } from './optionset.model';

export const sampleWithRequiredData: IOptionset = {
  id: '352e3b80-3f9c-4703-901f-f848053defcc',
};

export const sampleWithPartialData: IOptionset = {
  id: '037dfe3d-50c6-4deb-9f2e-5b3eaad76a97',
  name: 'a Seamless',
};

export const sampleWithFullData: IOptionset = {
  id: 'f3aeb200-a875-4d20-87e7-78e75c81c395',
  name: 'Rand transmitter lavender',
};

export const sampleWithNewData: NewOptionset = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
