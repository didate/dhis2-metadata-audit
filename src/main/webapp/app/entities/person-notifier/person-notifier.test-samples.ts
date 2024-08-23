import { IPersonNotifier, NewPersonNotifier } from './person-notifier.model';

export const sampleWithRequiredData: IPersonNotifier = {
  id: 84263,
  personName: 'la Bedfordshire',
  personPhone: 'overriding partnerships index',
  personEmail: '/TQ@ZW.zB2a&Y',
  personOrganization: 'Games',
};

export const sampleWithPartialData: IPersonNotifier = {
  id: 82055,
  personName: 'Bedfordshire Dominique multi-byte',
  personPhone: 'Yemeni firewall Licensed',
  personEmail: 'VkBY@3(;p.R(RT?p',
  personOrganization: 'up relationships Rubber',
};

export const sampleWithFullData: IPersonNotifier = {
  id: 23437,
  personName: 'payment',
  personPhone: 'hardware',
  personEmail: 'jA@LF[L.~KYB&',
  personOrganization: 'Intelligent Account',
};

export const sampleWithNewData: NewPersonNotifier = {
  personName: 'pink',
  personPhone: 'payment',
  personEmail: '"?a`k@!J}g$Q.-&{',
  personOrganization: 'Leone Assistant',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
