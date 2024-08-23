import dayjs from 'dayjs/esm';

import { IDHISUser, NewDHISUser } from './dhis-user.model';

export const sampleWithRequiredData: IDHISUser = {
  id: '5b749c14-fe2b-4113-8334-c95511ba363d',
  name: 'juriste totalement à la merci',
  username: 'magnifique large moquer',
  track: 'NONE',
};

export const sampleWithPartialData: IDHISUser = {
  id: '5fd155df-75e9-4704-92b4-0d553c2d9a5a',
  code: 'renvoyer en vérité refaire',
  name: "par suite de à l'entour de chez",
  username: 'miam convertir',
  lastLogin: dayjs('2024-08-17T14:38'),
  phoneNumber: 'vouloir employer population du Québec',
  disabled: false,
  created: dayjs('2024-08-16T16:53'),
  track: 'UPDATE',
};

export const sampleWithFullData: IDHISUser = {
  id: '4f42d87b-8eca-4704-85d0-dae21929d888',
  code: 'cocorico enseigner',
  name: 'quand ?',
  displayName: 'déposer pschitt envers',
  username: 'par équipe de recherche',
  lastLogin: dayjs('2024-08-17T06:18'),
  email: 'Amaryllis30@gmail.com',
  phoneNumber: 'améliorer',
  disabled: true,
  passwordLastUpdated: dayjs('2024-08-17T12:50'),
  created: dayjs('2024-08-17T12:33'),
  lastUpdated: dayjs('2024-08-16T21:54'),
  track: 'NEW',
};

export const sampleWithNewData: NewDHISUser = {
  name: 'tant',
  username: 'à la merci population du Québec lâche',
  track: 'NEW',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
