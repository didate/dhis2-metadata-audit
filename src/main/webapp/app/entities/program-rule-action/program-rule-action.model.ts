import dayjs from 'dayjs/esm';
import { IProject } from 'app/entities/project/project.model';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { IProgramRule } from 'app/entities/program-rule/program-rule.model';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { IOptionGroup } from 'app/entities/option-group/option-group.model';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

export interface IProgramRuleAction {
  revisionNumber?: number | null;
  isSelected?: boolean | null;
  id: string;
  lastUpdated?: dayjs.Dayjs | null;
  created?: dayjs.Dayjs | null;
  programRuleActionType?: string | null;
  evaluationTime?: string | null;
  data?: string | null;
  templateUid?: string | null;
  content?: string | null;
  displayContent?: string | null;
  track?: TypeTrack | null;
  project?: Pick<IProject, 'id'> | null;
  createdBy?: Pick<IDHISUser, 'name'> | null;
  lastUpdatedBy?: Pick<IDHISUser, 'name'> | null;
  programRule?: IProgramRule | null;
  trackedEntityAttribute?: Pick<ITrackedEntityAttribute, 'id'> | null;
  dataElement?: Pick<ITrackedEntityAttribute, 'id'> | null;
  optionGroup?: Pick<IOptionGroup, 'id'> | null;
}

export type NewProgramRuleAction = Omit<IProgramRuleAction, 'id'> & { id: null };
