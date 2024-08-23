import dayjs from 'dayjs/esm';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgram } from 'app/entities/program/program.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IProgramIndicator {
  id: string;
  name?: string | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  shortName?: string | null;
  dimensionItemType?: string | null;
  expression?: string | null;
  filter?: string | null;
  analyticsType?: string | null;
  dimensionItem?: string | null;
  displayShortName?: string | null;
  displayName?: string | null;
  displayFormName?: string | null;
  track?: TypeTrack | null;
  createdBy?: Pick<IDHISUser, 'id'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'id'> | null;
  program?: Pick<IProgram, 'id'> | null;
  programs?: Pick<IProgram, 'id'>[] | null;
}

export type NewProgramIndicator = Omit<IProgramIndicator, 'id'> & { id: null };
