export interface IIndicatortype {
  id: string;
  name?: string | null;
}

export type NewIndicatortype = Omit<IIndicatortype, 'id'> & { id: null };
