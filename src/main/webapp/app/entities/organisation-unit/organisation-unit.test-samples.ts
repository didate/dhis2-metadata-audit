import dayjs from 'dayjs/esm';

import { IOrganisationUnit, NewOrganisationUnit } from './organisation-unit.model';

export const sampleWithRequiredData: IOrganisationUnit = {
  id: 'cd943570-37aa-4910-8598-729c9841d54a',
  name: 'de crainte que contribuer',
  created: dayjs('2024-08-22T09:02'),
  lastUpdated: dayjs('2024-08-22T06:28'),
  openingDate: dayjs('2024-08-23T00:37'),
  track: 'NEW',
};

export const sampleWithPartialData: IOrganisationUnit = {
  id: '5a5a6e8f-63f1-4da1-b84b-325c798db232',
  name: 'derrière',
  created: dayjs('2024-08-22T10:55'),
  lastUpdated: dayjs('2024-08-22T06:33'),
  openingDate: dayjs('2024-08-22T09:22'),
  track: 'NEW',
};

export const sampleWithFullData: IOrganisationUnit = {
  id: 'dc929e37-fc14-47cf-ad4c-e7cdffe5fc2a',
  name: 'hors énorme',
  created: dayjs('2024-08-22T09:48'),
  lastUpdated: dayjs('2024-08-22T08:23'),
  path: 'dans',
  openingDate: dayjs('2024-08-23T00:47'),
  level: 10443,
  track: 'NEW',
};

export const sampleWithNewData: NewOrganisationUnit = {
  name: 'subito secours',
  created: dayjs('2024-08-22T20:11'),
  lastUpdated: dayjs('2024-08-22T18:07'),
  openingDate: dayjs('2024-08-23T00:38'),
  track: 'UPDATE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
