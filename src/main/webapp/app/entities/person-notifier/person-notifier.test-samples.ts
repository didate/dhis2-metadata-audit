import { IPersonNotifier, NewPersonNotifier } from './person-notifier.model';

export const sampleWithRequiredData: IPersonNotifier = {
  id: 29343,
  personName: 'parlementaire bzzz',
  personPhone: 'de lunatique',
  personEmail: 'K}H9^@"g.YGyy',
  personOrganization: "accéder d'entre foule",
};

export const sampleWithPartialData: IPersonNotifier = {
  id: 17301,
  personName: 'absolument hormis police',
  personPhone: 'même si',
  personEmail: '`=zH1@NLMOd.<sWBR',
  personOrganization: 'emporter paraître à même',
};

export const sampleWithFullData: IPersonNotifier = {
  id: 9092,
  personName: 'bien moyennant solitaire',
  personPhone: 'occuper dès que combler',
  personEmail: 'i@`BoJ2.z#Zr&',
  personOrganization: 'sédentaire quoique',
};

export const sampleWithNewData: NewPersonNotifier = {
  personName: 'affable raide',
  personPhone: 'vivace',
  personEmail: 'deD6a@\\3.C0Im',
  personOrganization: 'si bien que',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
