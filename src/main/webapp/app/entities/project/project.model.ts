export interface IProject {
  id: number;
  projectName?: string | null;
  dhis2URL?: string | null;
  dhis2Version?: number | null;
  token?: string | null;
  emailNotification?: boolean | null;
  notificationTime?: string | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
