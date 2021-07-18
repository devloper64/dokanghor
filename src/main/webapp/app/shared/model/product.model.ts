export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  image?: string;
  subCategoryName?: string;
  subCategoryId?: number;
  productTypeId?: number;
}

export const defaultValue: Readonly<IProduct> = {};
