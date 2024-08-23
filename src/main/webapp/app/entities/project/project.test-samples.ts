import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 7383,
  projectName: 'prout affranchir à la faveur de',
  dhis2URL: 'balayer extatique',
  dhis2Version: 2408.3,
  token: 'afin que',
  emailNotification: true,
};

export const sampleWithPartialData: IProject = {
  id: 32751,
  projectName: 'à seule fin de aussitôt que',
  dhis2URL: 'outre timide foule',
  dhis2Version: 1210.68,
  token: 'sous ramasser',
  emailNotification: false,
  notificationTime: 'tsoin-tsoin',
};

export const sampleWithFullData: IProject = {
  id: 16840,
  projectName: 'étouffer diplomate',
  dhis2URL: 'au point que',
  dhis2Version: 23656.95,
  token: 'ha ha',
  emailNotification: false,
  notificationTime: 'à partir de ronron quoique',
};

export const sampleWithNewData: NewProject = {
  projectName: 'relire étant donné que',
  dhis2URL: 'sauvage',
  dhis2Version: 20111.17,
  token: 'entre-temps',
  emailNotification: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
