export interface IOptionGroup {
  id: string;
}

export type NewOptionGroup = Omit<IOptionGroup, 'id'> & { id: null };
