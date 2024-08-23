import { IIndicatortype, NewIndicatortype } from './indicatortype.model';

export const sampleWithRequiredData: IIndicatortype = {
  id: '99bd1dc2-2ab9-4d90-917a-322d9c5d8653',
};

export const sampleWithPartialData: IIndicatortype = {
  id: '37035ef5-08e1-4b09-bbc5-51729e65be04',
  name: 'c',
};

export const sampleWithFullData: IIndicatortype = {
  id: '75b67f47-422c-4233-823d-d094497a3ce5',
  name: 'Specialiste back a',
};

export const sampleWithNewData: NewIndicatortype = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
