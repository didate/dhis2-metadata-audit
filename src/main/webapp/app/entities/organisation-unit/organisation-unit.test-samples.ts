import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IOrganisationUnit, NewOrganisationUnit } from './organisation-unit.model';

export const sampleWithRequiredData: IOrganisationUnit = {
  id: '4af0c09f-309f-49f3-8d2c-608e536c0684',
  name: 'Saint-Honoré static Poitou-Charentes',
  created: dayjs('2024-08-23T01:59'),
  lastUpdated: dayjs('2024-08-23T04:44'),
  openingDate: dayjs('2024-08-23T03:43'),
  track: TypeTrack['NEW'],
};

export const sampleWithPartialData: IOrganisationUnit = {
  id: 'd68fb4ae-584a-4a0f-ad3d-526acef79b5b',
  name: "as Concrete d'Argenteuil",
  created: dayjs('2024-08-23T12:46'),
  lastUpdated: dayjs('2024-08-23T01:17'),
  openingDate: dayjs('2024-08-22T22:48'),
  track: TypeTrack['NONE'],
};

export const sampleWithFullData: IOrganisationUnit = {
  id: 'c1d2b518-94fa-4266-bef6-f0cafdfffa82',
  name: 'Sleek intangible',
  created: dayjs('2024-08-23T04:30'),
  lastUpdated: dayjs('2024-08-23T09:19'),
  path: 'Bedfordshire',
  openingDate: dayjs('2024-08-23T17:54'),
  level: 96823,
  track: TypeTrack['UPDATE'],
};

export const sampleWithNewData: NewOrganisationUnit = {
  name: 'empower c SMTP',
  created: dayjs('2024-08-23T10:36'),
  lastUpdated: dayjs('2024-08-23T13:10'),
  openingDate: dayjs('2024-08-23T05:41'),
  track: TypeTrack['NONE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
