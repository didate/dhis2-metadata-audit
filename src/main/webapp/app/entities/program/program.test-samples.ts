import { IProgram, NewProgram } from './program.model';

export const sampleWithRequiredData: IProgram = {
  id: '4a9df881-e6a8-44b6-a340-8d5bb6f23756',
  track: 'NEW',
};

export const sampleWithPartialData: IProgram = {
  id: '458cbbb6-0e8a-49c8-80c8-427ed9a8602a',
  name: 'à peu près ronfler fonctionnaire',
  created: 'membre à vie pour heurter',
  shortName: 'insipide',
  incidentDateLabel: 'terne',
  displayIncidentDate: false,
  ignoreOverdueEvents: true,
  userRoles: "à l'entour de d'avec",
  programIndicators: 'biathlète',
  programRuleVariables: 'd’autant que mentionner grimper',
  selectEnrollmentDatesInFuture: true,
  selectIncidentDatesInFuture: true,
  trackedEntityType: 'doucement',
  expiryDays: 15127,
  maxTeiCountToReturn: 13602,
  displayEnrollmentDateLabel: 'restaurer',
  registration: false,
  withoutRegistration: false,
  displayShortName: 'rassurer puis proche',
  displayDescription: 'partir',
  programTrackedEntityAttributesCount: 2228,
  track: 'UPDATE',
};

export const sampleWithFullData: IProgram = {
  id: '5e9c9bcb-338c-4ca7-a76f-f9d2758493bc',
  name: 'résister bè cependant',
  created: 'tellement',
  lastUpdated: 'même si gigantesque',
  shortName: 'autour de par',
  description: 'foule',
  version: 32569.74,
  enrollmentDateLabel: 'secouriste',
  incidentDateLabel: 'ha',
  programType: 'avant que',
  displayIncidentDate: false,
  ignoreOverdueEvents: false,
  userRoles: 'mélancolique hi bien que',
  programIndicators: 'vu que',
  programRuleVariables: 'contre',
  onlyEnrollOnce: true,
  notificationTemplates: 'membre de l’équipe',
  selectEnrollmentDatesInFuture: false,
  selectIncidentDatesInFuture: false,
  trackedEntityType: 'bè au-delà contraindre',
  style: 'lors bzzz rendre',
  skipOffline: true,
  displayFrontPageList: true,
  useFirstStageDuringRegistration: true,
  expiryDays: 31179.18,
  completeEventsExpiryDays: 27713.81,
  openDaysAfterCoEndDate: 20355.43,
  minAttributesRequiredToSearch: 1818.34,
  maxTeiCountToReturn: 11007.67,
  accessLevel: 'moyennant comme membre titulaire',
  displayEnrollmentDateLabel: 'splendide',
  displayIncidentDateLabel: 'loufoque',
  registration: true,
  withoutRegistration: false,
  displayShortName: 'ouf',
  displayDescription: 'quand ? tranquille personnel professionnel',
  displayFormName: 'mairie pin-pon',
  displayName: 'areu areu',
  attributeValuesCount: 6617,
  organisationUnitsCount: 14460,
  programStagesCount: 1596,
  programSectionsCount: 11437,
  programTrackedEntityAttributesCount: 27060,
  track: 'NONE',
};

export const sampleWithNewData: NewProgram = {
  track: 'NEW',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
