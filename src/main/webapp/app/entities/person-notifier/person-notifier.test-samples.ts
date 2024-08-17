import { IPersonNotifier, NewPersonNotifier } from './person-notifier.model';

export const sampleWithRequiredData: IPersonNotifier = {
  id: 20269,
  personName: 'zzzz horrible exécuter',
  personPhone: 'touchant bénéficier',
  personEmail: 'B)"@xSpdW.?0WV-',
  personOrganization: 'coac coac',
};

export const sampleWithPartialData: IPersonNotifier = {
  id: 16295,
  personName: 'bien que décharger',
  personPhone: 'animer trop',
  personEmail: 'm<:Nnh@76.IE/q',
  personOrganization: 'plic au-dedans de membre titulaire',
};

export const sampleWithFullData: IPersonNotifier = {
  id: 22890,
  personName: 'au lieu de tchou tchouu',
  personPhone: 'lunatique diététiste',
  personEmail: "4fU@3(_~'.hn3|",
  personOrganization: 'procéder céans',
};

export const sampleWithNewData: NewPersonNotifier = {
  personName: 'cacher dedans demeurer',
  personPhone: 'résumer collègue',
  personEmail: 'px@MQ).?,-',
  personOrganization: 'dense commissionnaire reprendre',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
