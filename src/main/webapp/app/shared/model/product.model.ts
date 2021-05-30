export interface IProduct {
  id?: number;
  name?: string;
  price?: string;
  subCategoryName?: string;
  subCategoryId?: number;
}

export const defaultValue: Readonly<IProduct> = {};
