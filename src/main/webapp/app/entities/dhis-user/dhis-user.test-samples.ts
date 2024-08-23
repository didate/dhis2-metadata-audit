import dayjs from 'dayjs/esm';

import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IDHISUser, NewDHISUser } from './dhis-user.model';

export const sampleWithRequiredData: IDHISUser = {
  id: '85538e4c-01db-498e-a192-14b12dd1a57b',
  name: 'Languedoc-Roussillon',
  username: 'USB leverage Franc',
  track: TypeTrack['UPDATE'],
};

export const sampleWithPartialData: IDHISUser = {
  id: '440906d6-46f5-49fa-b109-6d61e45878fc',
  name: 'Leu applications',
  displayName: 'ubiquitous',
  username: 'technologies Industrial',
  lastLogin: dayjs('2024-08-23T00:02'),
  disabled: true,
  created: dayjs('2024-08-23T19:02'),
  track: TypeTrack['NEW'],
};

export const sampleWithFullData: IDHISUser = {
  id: 'e501ed7f-bf35-4647-9b24-9ef86f4559b8',
  code: '24/7 holistic',
  name: 'circuit Bedfordshire',
  displayName: 'Steel deposit Concrete',
  username: 'transition withdrawal',
  lastLogin: dayjs('2024-08-23T08:14'),
  email: 'Aurian.Dupuis32@gmail.com',
  phoneNumber: 'bandwidth copy',
  disabled: true,
  passwordLastUpdated: dayjs('2024-08-23T01:20'),
  created: dayjs('2024-08-23T06:01'),
  lastUpdated: dayjs('2024-08-23T03:34'),
  track: TypeTrack['NEW'],
};

export const sampleWithNewData: NewDHISUser = {
  name: 'Auvergne des',
  username: 'Fresh supply-chains',
  track: TypeTrack['NEW'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
