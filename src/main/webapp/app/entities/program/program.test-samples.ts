import { TypeTrack } from 'app/entities/enumerations/type-track.model';

import { IProgram, NewProgram } from './program.model';

export const sampleWithRequiredData: IProgram = {
  id: 'be952de6-bc41-46e5-ab53-bcf54939440c',
  track: TypeTrack['NEW'],
};

export const sampleWithPartialData: IProgram = {
  id: 'a935ad90-fd22-4073-ba87-d7efac44fd20',
  name: 'SSL',
  description: 'Steel payment',
  enrollmentDateLabel: 'withdrawal Rubber',
  incidentDateLabel: 'salmon c multi-byte',
  displayIncidentDate: false,
  notificationTemplates: 'Chips',
  displayFrontPageList: false,
  completeEventsExpiryDays: 60910,
  maxTeiCountToReturn: 53122,
  displayEnrollmentDateLabel: 'payment maximized Loan',
  displayShortName: 'solution-oriented',
  displayDescription: '1080p Refined c',
  organisationUnitsCount: 62062,
  programStagesCount: 73540,
  programIndicatorsCount: 52193,
  programStagesContent: 'online firmware',
  track: TypeTrack['UPDATE'],
};

export const sampleWithFullData: IProgram = {
  id: '7d67d987-dc7f-49da-ac4f-621f2aa32d6d',
  name: 'Manager Computer Metal',
  created: 'archive Towels',
  lastUpdated: 'line',
  shortName: 'SMS',
  description: 'encryption Account',
  version: 89663,
  enrollmentDateLabel: 'Salad',
  incidentDateLabel: 'next User-friendly Automotive',
  programType: 'Assistant',
  displayIncidentDate: false,
  ignoreOverdueEvents: true,
  userRoles: 'du Midi-Pyrénées cross-platform',
  onlyEnrollOnce: false,
  notificationTemplates: 'black Bike',
  selectEnrollmentDatesInFuture: false,
  selectIncidentDatesInFuture: true,
  trackedEntityType: 'c',
  style: 'payment Nauru Refined',
  skipOffline: true,
  displayFrontPageList: false,
  useFirstStageDuringRegistration: false,
  expiryDays: 99018,
  completeEventsExpiryDays: 59294,
  openDaysAfterCoEndDate: 24403,
  minAttributesRequiredToSearch: 80574,
  maxTeiCountToReturn: 27816,
  accessLevel: 'de Chair',
  displayEnrollmentDateLabel: 'des deposit',
  displayIncidentDateLabel: 'Up-sized',
  registration: true,
  withoutRegistration: true,
  displayShortName: 'programming Bedfordshire open-source',
  displayDescription: 'copying connecting',
  displayFormName: 'orange b Games',
  displayName: 'b',
  organisationUnitsCount: 38257,
  programStagesCount: 80895,
  programIndicatorsCount: 69633,
  programTrackedEntityAttributesCount: 41921,
  organisationUnitsContent: 'Corse Pants',
  programStagesContent: 'encompassing standardization innovate',
  programIndicatorsContent: 'redundant input',
  programTrackedEntityAttributesContent: 'connect Intelligent sensor',
  track: TypeTrack['NEW'],
};

export const sampleWithNewData: NewProgram = {
  track: TypeTrack['NONE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
