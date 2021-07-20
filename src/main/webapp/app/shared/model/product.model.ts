export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  image?: string;
  subCategoryName?: string;
  subCategoryId?: number;
  productTypeId?: number;
  discount_amount?: number;
  product_details?: string;
}

export const defaultValue: Readonly<IProduct> = {};
