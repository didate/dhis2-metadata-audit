import dayjs from 'dayjs/esm';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IDHISUser {
  revisionNumber?: number | null;
  isSelected?: boolean | null;
  id: string;
  code?: string | null;
  name?: string | null;
  displayName?: string | null;
  username?: string | null;
  lastLogin?: dayjs.Dayjs | null;
  email?: string | null;
  phoneNumber?: string | null;
  disabled?: boolean | null;
  passwordLastUpdated?: dayjs.Dayjs | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  track?: TypeTrack | null;
  createdBy?: Pick<IDHISUser, 'username'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'username'> | null;
}

export type NewDHISUser = Omit<IDHISUser, 'id'> & { id: null };
