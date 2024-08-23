import { IIndicatortype, NewIndicatortype } from './indicatortype.model';

export const sampleWithRequiredData: IIndicatortype = {
  id: '6562b4a0-3742-42ba-9675-d4051faf62fc',
};

export const sampleWithPartialData: IIndicatortype = {
  id: '7eef8852-7afd-406c-9c54-eb865784a1cb',
  name: "miaou à l'égard de",
};

export const sampleWithFullData: IIndicatortype = {
  id: 'c7211f50-023b-4a70-acfc-4ea8ffa6d1c9',
  name: 'ouille de crainte que',
};

export const sampleWithNewData: NewIndicatortype = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
