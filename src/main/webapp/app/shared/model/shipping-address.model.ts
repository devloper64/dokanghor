export interface IShippingAddress {
  id?: number;
  district?: string;
  upazila?: string;
  postalcode?: string;
  phoneNumber?: string;
  userId?: number;
}

export const defaultValue: Readonly<IShippingAddress> = {};
