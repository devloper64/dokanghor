export interface IAddress {
  id?: number;
  name?: string;
  postalCode?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IAddress> = {};
