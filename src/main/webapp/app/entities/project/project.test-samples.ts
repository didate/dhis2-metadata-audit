import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 55962,
  projectName: 'COM dedicated Agent',
  dhis2URL: 'Awesome users',
  dhis2Version: 77481,
  token: 'Personal enhance Borders',
  emailNotification: false,
};

export const sampleWithPartialData: IProject = {
  id: 6247,
  projectName: 'a b Loan',
  dhis2URL: 'Tasty secondary',
  dhis2Version: 43337,
  token: 'Nigeria Slovénie models',
  emailNotification: true,
};

export const sampleWithFullData: IProject = {
  id: 16934,
  projectName: 'payment',
  dhis2URL: 'backing Proactive',
  dhis2Version: 58117,
  token: 'c state',
  emailNotification: true,
  notificationTime: 'a Berkshire Cotton',
};

export const sampleWithNewData: NewProject = {
  projectName: 'fresh-thinking Granite EXE',
  dhis2URL: 'leading Developpeur',
  dhis2Version: 40753,
  token: 'systematic override Bacon',
  emailNotification: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
