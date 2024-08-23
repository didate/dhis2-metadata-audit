import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { IProgram } from 'app/entities/program/program.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface ITrackedEntityAttribute {
  id: string;
  lastUpdated?: dayjs.Dayjs | null;
  created?: dayjs.Dayjs | null;
  name?: string | null;
  shortName?: string | null;
  generated?: boolean | null;
  valueType?: string | null;
  confidential?: boolean | null;
  displayFormName?: string | null;
  uniquee?: boolean | null;
  dimensionItemType?: string | null;
  aggregationType?: string | null;
  displayInListNoProgram?: boolean | null;
  displayName?: string | null;
  patterne?: string | null;
  skipSynchronization?: boolean | null;
  displayShortName?: string | null;
  periodOffset?: number | null;
  displayOnVisitSchedule?: boolean | null;
  formName?: string | null;
  orgunitScope?: boolean | null;
  dimensionItem?: string | null;
  inherit?: boolean | null;
  optionSetValue?: boolean | null;
  track?: keyof typeof TypeTrack | null;
  project?: IProject | null;
  createdBy?: IDHISUser | null;
  lastUpdatedBy?: IDHISUser | null;
  optionSet?: IOptionset | null;
  programs?: IProgram[] | null;
}

export type NewTrackedEntityAttribute = Omit<ITrackedEntityAttribute, 'id'> & { id: null };
