export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  image?: string;
  subCategoryName?: string;
  subCategoryId?: number;
}

export const defaultValue: Readonly<IProduct> = {};