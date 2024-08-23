import { IIndicatortype, NewIndicatortype } from './indicatortype.model';

export const sampleWithRequiredData: IIndicatortype = {
  id: '2ba18426-238a-4871-8873-7164e76e7e84',
};

export const sampleWithPartialData: IIndicatortype = {
  id: '7d245dc5-46f1-4462-af00-665607e285ce',
  name: 'lectorat au-dehors',
};

export const sampleWithFullData: IIndicatortype = {
  id: '8b38d70e-e16c-4917-911f-3f7f4be012b1',
  name: 'de façon à ce que',
};

export const sampleWithNewData: NewIndicatortype = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
