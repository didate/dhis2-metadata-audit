import { IOptionset, NewOptionset } from './optionset.model';

export const sampleWithRequiredData: IOptionset = {
  id: 'e6c11133-c35e-44a7-914a-1d3ac8046617',
};

export const sampleWithPartialData: IOptionset = {
  id: '26373244-6c11-4227-8483-fcd4beb272ae',
};

export const sampleWithFullData: IOptionset = {
  id: 'be6cf5c9-d169-4760-ab43-2e252c71617f',
  name: 'si de par',
};

export const sampleWithNewData: NewOptionset = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
