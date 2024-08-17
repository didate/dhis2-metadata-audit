import { IOptionset, NewOptionset } from './optionset.model';

export const sampleWithRequiredData: IOptionset = {
  id: '93f33ecf-c17b-422e-b069-1f72fd440bab',
};

export const sampleWithPartialData: IOptionset = {
  id: '840c13f6-6c7b-4b2d-a545-97c22d996685',
  name: 'jusqu’à ce que',
};

export const sampleWithFullData: IOptionset = {
  id: '94b1c842-0256-48f1-80a5-226500a57286',
  name: 'près de dans',
};

export const sampleWithNewData: NewOptionset = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
