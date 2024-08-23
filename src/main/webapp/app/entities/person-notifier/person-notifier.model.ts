import { IProject } from 'app/entities/project/project.model';

export interface IPersonNotifier {
  id: number;
  personName?: string | null;
  personPhone?: string | null;
  personEmail?: string | null;
  personOrganization?: string | null;
  project?: IProject | null;
}

export type NewPersonNotifier = Omit<IPersonNotifier, 'id'> & { id: null };