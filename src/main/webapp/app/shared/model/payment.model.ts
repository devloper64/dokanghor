export interface IPayment {
  id?: number;
  amount?: number;
  userLogin?: string;
  userId?: number;
  productId?: number;
  shippingAddressId?: number;
}

export const defaultValue: Readonly<IPayment> = {};
