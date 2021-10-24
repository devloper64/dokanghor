export interface IShippingAddress {
  id?: number;
  district?: string;
  upazila?: string;
  postalcode?: string;
  phoneNumber?: string;
  userId?: number;
  divisions?: string;
}

export const defaultValue: Readonly<IShippingAddress> = {};
