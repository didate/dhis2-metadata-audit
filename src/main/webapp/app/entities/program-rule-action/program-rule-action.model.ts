import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgramRule } from 'app/entities/program-rule/program-rule.model';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { IOptionGroup } from 'app/entities/option-group/option-group.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IProgramRuleAction {
  id: string;
  lastUpdated?: dayjs.Dayjs | null;
  created?: string | null;
  programRuleActionType?: string | null;
  evaluationTime?: string | null;
  data?: string | null;
  templateUid?: string | null;
  content?: string | null;
  displayContent?: string | null;
  track?: keyof typeof TypeTrack | null;
  project?: IProject | null;
  createdBy?: IDHISUser | null;
  lastUpdatedBy?: IDHISUser | null;
  programRule?: IProgramRule | null;
  trackedEntityAttribute?: ITrackedEntityAttribute | null;
  dataElement?: ITrackedEntityAttribute | null;
  optionGroup?: IOptionGroup | null;
}

export type NewProgramRuleAction = Omit<IProgramRuleAction, 'id'> & { id: null };
