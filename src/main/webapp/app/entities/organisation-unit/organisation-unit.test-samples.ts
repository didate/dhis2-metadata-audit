import dayjs from 'dayjs/esm';

import { IOrganisationUnit, NewOrganisationUnit } from './organisation-unit.model';

export const sampleWithRequiredData: IOrganisationUnit = {
  id: '497372dc-98ae-4569-8829-c41bd37ea29e',
  name: 'coac coac',
  created: dayjs('2024-08-22T13:26'),
  lastUpdated: dayjs('2024-08-22T21:51'),
  openingDate: dayjs('2024-08-22T02:43'),
  track: 'NEW',
};

export const sampleWithPartialData: IOrganisationUnit = {
  id: '7c6f5691-5233-484e-afe9-d6c803d55af1',
  name: 'apparemment pour que',
  created: dayjs('2024-08-22T11:07'),
  lastUpdated: dayjs('2024-08-22T11:15'),
  openingDate: dayjs('2024-08-22T05:42'),
  track: 'NONE',
};

export const sampleWithFullData: IOrganisationUnit = {
  id: '76b63984-da70-4f80-b6f6-0c4184fa9296',
  name: 'ha ha émérite atchoum',
  created: dayjs('2024-08-22T20:27'),
  lastUpdated: dayjs('2024-08-22T02:54'),
  path: 'extrêmement',
  openingDate: dayjs('2024-08-22T19:16'),
  level: 32348,
  track: 'NONE',
};

export const sampleWithNewData: NewOrganisationUnit = {
  name: 'présenter',
  created: dayjs('2024-08-22T18:53'),
  lastUpdated: dayjs('2024-08-22T21:09'),
  openingDate: dayjs('2024-08-22T18:31'),
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
