import { IProductDetails } from 'app/shared/model/product-details.model';

export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  image?: string;
  subCategoryName?: string;
  subCategoryId?: number;
  productTypeId?: number;
  discount_amount?: number;
  quantity?: number;
  productDetails?: IProductDetails;
  userId?: number;
}

export const defaultValue: Readonly<IProduct> = {};
