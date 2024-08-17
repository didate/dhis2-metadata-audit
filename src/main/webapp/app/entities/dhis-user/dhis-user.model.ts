export interface IDHISUser {
  id: string;
  code?: string | null;
  name?: string | null;
  displayName?: string | null;
  username?: string | null;
}

export type NewDHISUser = Omit<IDHISUser, 'id'> & { id: null };
