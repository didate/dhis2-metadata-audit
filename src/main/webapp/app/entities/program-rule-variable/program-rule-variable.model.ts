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
  track?: TypeTrack | null;
  project?: Pick<IProject, 'id'> | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
  program?: Pick<IProgram, 'id'> | null;
  trackedEntityAttribute?: Pick<ITrackedEntityAttribute, 'id'> | null;
  dataElement?: Pick<IDataelement, 'id'> | null;
}

export type NewProgramRuleVariable = Omit<IProgramRuleVariable, 'id'> & { id: null };
