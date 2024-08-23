import dayjs from 'dayjs/esm';

import { IDHISUser, NewDHISUser } from './dhis-user.model';

export const sampleWithRequiredData: IDHISUser = {
  id: '957b627e-db5a-40c0-912d-0046bd36e0e9',
  name: 'neutre',
  username: 'ha protéger',
  track: 'NONE',
};

export const sampleWithPartialData: IDHISUser = {
  id: '3a7c3cf1-be1e-4bdb-9504-1056a3ad2832',
  name: 'précisément décourager',
  displayName: 'renouveler dominer',
  username: 'marron',
  email: 'Merlin.Lefevre41@hotmail.fr',
  phoneNumber: 'équipe sympathique',
  created: dayjs('2024-08-17T06:03'),
  lastUpdated: dayjs('2024-08-17T13:56'),
  track: 'NONE',
};

export const sampleWithFullData: IDHISUser = {
  id: '6dea7c1a-d61d-4e37-a21c-f139cdd55671',
  code: 'moyennant habile au-dedans de',
  name: 'bzzz lors',
  displayName: 'en guise de que sans que',
  username: 'ouin',
  lastLogin: dayjs('2024-08-17T00:12'),
  email: 'Adhemar_Roux2@yahoo.fr',
  phoneNumber: 'boum tant lunatique',
  disabled: true,
  passwordLastUpdated: dayjs('2024-08-17T10:02'),
  created: dayjs('2024-08-17T01:13'),
  lastUpdated: dayjs('2024-08-17T14:14'),
  track: 'NONE',
};

export const sampleWithNewData: NewDHISUser = {
  name: 'apparemment trop',
  username: 'tsoin-tsoin vouh',
  track: 'NONE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
