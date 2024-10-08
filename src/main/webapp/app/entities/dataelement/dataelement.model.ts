import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { IProgramStage } from 'app/entities/program-stage/program-stage.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IDataelement {
  revisionNumber?: number | null;
  isSelected?: boolean | null;
  id: string;
  name?: string | null;
  shortName?: string | null;
  formName?: string | null;
  description?: string | null;
  displayShortName?: string | null;
  displayName?: string | null;
  displayFormName?: string | null;
  created?: dayjs.Dayjs | null;
  lastUpdated?: dayjs.Dayjs | null;
  publicAccess?: string | null;
  dimensionItemType?: string | null;
  aggregationType?: string | null;
  valueType?: string | null;
  domainType?: string | null;
  zeroIsSignificant?: boolean | null;
  optionSetValue?: string | null;
  dimensionItem?: string | null;
  track?: TypeTrack | null;
  project?: Pick<IProject, 'id'> | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
  categoryCombo?: Pick<ICategorycombo, 'id'> | null;
  optionSet?: Pick<IOptionset, 'id'> | null;
  dataSets?: Pick<IDataset, 'id'>[] | null;
  programStages?: Pick<IProgramStage, 'id'>[] | null;
}

export type NewDataelement = Omit<IDataelement, 'id'> & { id: null };
