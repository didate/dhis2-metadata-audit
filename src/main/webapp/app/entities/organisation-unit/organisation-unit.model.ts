import dayjs from 'dayjs/esm';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IOrganisationUnit {
  revisionNumber?: number | null;
  isSelected?: boolean | null;
  id: string;
  name?: string | null;
  path?: string | null;
  openingDate?: dayjs.Dayjs | null;
  level?: number | null;
  track?: TypeTrack | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
  programs?: Pick<IProgram, 'id'>[] | null;
  dataSets?: Pick<IDataset, 'id'>[] | null;
}

export type NewOrganisationUnit = Omit<IOrganisationUnit, 'id'> & { id: null };
