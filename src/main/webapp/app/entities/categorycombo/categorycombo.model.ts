export interface ICategorycombo {
  id: string;
  name?: string | null;
}

export type NewCategorycombo = Omit<ICategorycombo, 'id'> & { id: null };
