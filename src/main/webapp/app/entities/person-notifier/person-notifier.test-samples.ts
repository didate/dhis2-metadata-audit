import { IPersonNotifier, NewPersonNotifier } from './person-notifier.model';

export const sampleWithRequiredData: IPersonNotifier = {
  id: 6206,
  personName: 'groin groin boum',
  personPhone: 'trop aimable',
  personEmail: 'V@c[c{0m.%OP',
  personOrganization: 'quitte à porte-parole',
};

export const sampleWithPartialData: IPersonNotifier = {
  id: 24273,
  personName: 'ouch dynamique guide',
  personPhone: 'patientèle oh communauté étudiante',
  personEmail: '~~@xgqly.:D',
  personOrganization: 'désormais',
};

export const sampleWithFullData: IPersonNotifier = {
  id: 6578,
  personName: 'sale avant',
  personPhone: 'puisque chauffer',
  personEmail: '(tq]@Y-V^aI.5D',
  personOrganization: 'fabriquer altruiste',
};

export const sampleWithNewData: NewPersonNotifier = {
  personName: 'administration aïe areu areu',
  personPhone: 'prendre mordre',
  personEmail: 'I#-s@"M\\FLc.SC>a\'',
  personOrganization: 'afin que préciser',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
