import { IProduct } from 'app/shared/model/product.model';

export interface IPayment {
  id?: number;
  amount?: number;
  userLogin?: string;
  userId?: number;
  products?: IProduct[];
  shippingAddressId?: number;
}

export const defaultValue: Readonly<IPayment> = {};
