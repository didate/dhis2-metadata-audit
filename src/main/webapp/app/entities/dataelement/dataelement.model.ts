import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { IProgram } from 'app/entities/program/program.model';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IDataelement {
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
  track?: keyof typeof TypeTrack | null;
  project?: IProject | null;
  createdBy?: IDHISUser | null;
  lastUpdatedBy?: IDHISUser | null;
  categoryCombo?: ICategorycombo | null;
  optionSet?: IOptionset | null;
  programs?: IProgram[] | null;
  datasets?: IDataset[] | null;
}

export type NewDataelement = Omit<IDataelement, 'id'> & { id: null };
