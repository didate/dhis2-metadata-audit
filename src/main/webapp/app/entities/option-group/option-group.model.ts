import dayjs from 'dayjs/esm';
import { IDHISUser } from '../dhis-user/dhis-user.model';

export interface IOptionGroup {
  revisionNumber?: number | null;
  isSelected?: boolean | null;
  id: string;

  name?: string | null;

  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
}

export type NewOptionGroup = Omit<IOptionGroup, 'id'> & { id: null };
