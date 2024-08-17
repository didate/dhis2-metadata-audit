import { IIndicatortype, NewIndicatortype } from './indicatortype.model';

export const sampleWithRequiredData: IIndicatortype = {
  id: '5560f44b-8b44-455b-bf7e-75ec1e8d3afb',
};

export const sampleWithPartialData: IIndicatortype = {
  id: 'a53acbb2-0e16-485e-8913-9c51c84a469e',
  name: 'guide de crainte que groin groin',
};

export const sampleWithFullData: IIndicatortype = {
  id: '664036d8-dd51-4022-b1f4-29679ee99a4a',
  name: 'membre à vie',
};

export const sampleWithNewData: NewIndicatortype = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
