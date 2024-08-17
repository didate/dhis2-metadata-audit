import { IDHISUser, NewDHISUser } from './dhis-user.model';

export const sampleWithRequiredData: IDHISUser = {
  id: 'bdb4df66-053c-4f4a-b226-4bd67dd0480b',
  name: "garantir quand ? aujourd'hui",
  username: 'perplexe',
};

export const sampleWithPartialData: IDHISUser = {
  id: '3a595bf1-5a6d-44ee-a82d-ef02a9f2ecdd',
  name: 'organiser',
  username: 'pin-pon envers chef',
};

export const sampleWithFullData: IDHISUser = {
  id: 'd658e90e-8bf6-4f3d-9e45-5fb0ad7ed606',
  code: 'jeune enfant bang au point que',
  name: 'en vérité selon',
  displayName: 'loin de lorsque autour de',
  username: 'pauvre jusqu’à ce que juriste',
};

export const sampleWithNewData: NewDHISUser = {
  name: 'au point que concernant',
  username: 'candide patientèle',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
