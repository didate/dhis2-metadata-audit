import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';

export interface IDataset {
  id: string;
  name?: string | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  shortName?: string | null;
  description?: string | null;
  dimensionItemType?: string | null;
  periodType?: string | null;
  categoryCombo?: string | null;
  mobile?: string | null;
  version?: number | null;
  expiryDays?: number | null;
  timelyDays?: number | null;
  notifyCompletingUser?: string | null;
  openFuturePeriods?: number | null;
  openPeriodsAfterCoEndDate?: number | null;
  fieldCombinationRequired?: boolean | null;
  validCompleteOnly?: boolean | null;
  noValueRequiresComment?: boolean | null;
  skipOffline?: boolean | null;
  dataElementDecoration?: boolean | null;
  renderAsTabs?: boolean | null;
  renderHorizontally?: boolean | null;
  compulsoryFieldsCompleteOnly?: boolean | null;
  formType?: string | null;
  displayName?: string | null;
  dimensionItem?: string | null;
  displayShortName?: string | null;
  displayDescription?: string | null;
  displayFormName?: string | null;
  project?: IProject | null;
  createdBy?: IDHISUser | null;
  lastUpdatedBy?: IDHISUser | null;
}

export type NewDataset = Omit<IDataset, 'id'> & { id: null };
