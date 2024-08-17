export interface IOptionset {
  id: string;
  name?: string | null;
}

export type NewOptionset = Omit<IOptionset, 'id'> & { id: null };
