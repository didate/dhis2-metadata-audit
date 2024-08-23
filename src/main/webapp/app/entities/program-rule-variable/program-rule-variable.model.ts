import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IProgramRuleVariable {
  id: string;
  lastUpdated?: dayjs.Dayjs | null;
  created?: dayjs.Dayjs | null;
  name?: string | null;
  displayName?: string | null;
  programRuleVariableSourceType?: string | null;
  useCodeForOptionSet?: boolean | null;
  track?: keyof typeof TypeTrack | null;
  project?: IProject | null;
  createdBy?: IDHISUser | null;
  lastUpdatedBy?: IDHISUser | null;
  program?: IProgram | null;
  trackedEntityAttribute?: ITrackedEntityAttribute | null;
  dataElement?: IDataelement | null;
}

export type NewProgramRuleVariable = Omit<IProgramRuleVariable, 'id'> & { id: null };
