import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 31349,
  projectName: 'annoncer afin de ça',
  dhis2URL: 'derrière grâce à équipe',
  dhis2Version: 3275.89,
  token: 'coordonner aimable pacifique',
  emailNotification: true,
};

export const sampleWithPartialData: IProject = {
  id: 5666,
  projectName: 'novice',
  dhis2URL: "d'entre",
  dhis2Version: 20464.22,
  token: 'rudement assurément exiger',
  emailNotification: false,
  notificationTime: 'trancher population du Québec tremper',
};

export const sampleWithFullData: IProject = {
  id: 9752,
  projectName: 'arrière',
  dhis2URL: 'prestataire de services',
  dhis2Version: 378.34,
  token: 'jusqu’à ce que',
  emailNotification: false,
  notificationTime: 'amorphe groin groin',
};

export const sampleWithNewData: NewProject = {
  projectName: 'aïe malgré',
  dhis2URL: 'immense ouille prononcer',
  dhis2Version: 10896.26,
  token: 'séculaire tromper peut-être',
  emailNotification: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
