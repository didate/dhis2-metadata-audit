import dayjs from 'dayjs/esm';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IOrganisationUnit {
  id: string;
  name?: string | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  path?: string | null;
  openingDate?: dayjs.Dayjs | null;
  level?: number | null;
  track?: keyof typeof TypeTrack | null;
  createdBy?: IDHISUser | null;
  lastUpdatedBy?: IDHISUser | null;
  programs?: IProgram[] | null;
  datasets?: IDataset[] | null;
}

export type NewOrganisationUnit = Omit<IOrganisationUnit, 'id'> & { id: null };
