export interface IShippingAddress {
  id?: number;
  district?: string;
  upazila?: string;
  postalcode?: string;
}

export const defaultValue: Readonly<IShippingAddress> = {};
