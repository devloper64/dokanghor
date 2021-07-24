export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  image?: string;
  subCategoryName?: string;
  subCategoryId?: number;
  productTypeId?: number;
  discount_amount?: number;
  productDetailsId?: number;
}

export const defaultValue: Readonly<IProduct> = {};
