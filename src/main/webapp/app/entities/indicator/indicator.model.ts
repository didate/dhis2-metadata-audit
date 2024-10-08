import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IIndicatortype } from 'app/entities/indicatortype/indicatortype.model';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IIndicator {
  revisionNumber?: number | null;
  isSelected?: boolean | null;
  id: string;
  name?: string | null;
  shortName?: string | null;
  displayShortName?: string | null;
  displayName?: string | null;
  displayFormName?: string | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  publicAccess?: string | null;
  dimensionItemType?: string | null;
  annualized?: boolean | null;
  numerator?: string | null;
  numeratorDescription?: string | null;
  denominator?: string | null;
  denominatorDescription?: string | null;
  displayNumeratorDescription?: string | null;
  displayDenominatorDescription?: string | null;
  dimensionItem?: string | null;
  track?: TypeTrack | null;
  project?: Pick<IProject, 'id'> | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
  indicatorType?: Pick<IIndicatortype, 'name'> | null;
  dataSets?: Pick<IDataset, 'id'>[] | null;
}

export type NewIndicator = Omit<IIndicator, 'id'> & { id: null };
