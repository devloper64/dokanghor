import { IProduct } from 'app/shared/model/product.model';

export interface IPayment {
  id?: number;
  totalAmount?: number;
  userLogin?: string;
  userId?: number;
  products?: IProduct[];
  shippingAddressId?: number;
  productQuantities?: string;
  individualAmount?: string;
  active?: boolean;
}

export const defaultValue: Readonly<IPayment> = {};
