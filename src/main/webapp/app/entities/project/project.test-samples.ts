import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 28294,
  projectName: 'fade près de rectorat',
  dhis2URL: 'pour que écraser vroum',
  dhis2Version: 19541.56,
  token: 'avare',
  emailNotification: true,
};

export const sampleWithPartialData: IProject = {
  id: 19648,
  projectName: 'tic-tac ouf vivace',
  dhis2URL: 'd’autant que personnel professionnel',
  dhis2Version: 22536.14,
  token: 'calme',
  emailNotification: true,
};

export const sampleWithFullData: IProject = {
  id: 4745,
  projectName: 'euh',
  dhis2URL: 'ouch puisque rentrer',
  dhis2Version: 22064.13,
  token: 'au-delà communauté étudiante résumer',
  emailNotification: true,
};

export const sampleWithNewData: NewProject = {
  projectName: 'suivant',
  dhis2URL: 'admirablement raide envers',
  dhis2Version: 5381.96,
  token: 'divinement',
  emailNotification: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
