import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IProgramRule {
  id: string;
  lastUpdated?: dayjs.Dayjs | null;
  created?: dayjs.Dayjs | null;
  name?: string | null;
  displayName?: string | null;
  priority?: number | null;
  condition?: string | null;
  track?: TypeTrack | null;
  project?: Pick<IProject, 'id'> | null;
  createdBy?: Pick<IDHISUser, 'id'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'id'> | null;
  program?: Pick<IProgram, 'id'> | null;
}

export type NewProgramRule = Omit<IProgramRule, 'id'> & { id: null };
