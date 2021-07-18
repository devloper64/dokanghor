import { IProduct } from 'app/shared/model/product.model';

export interface IProductType {
  id?: number;
  name?: string;
  subCategoryId?: number;
  products?: IProduct[];
}

export const defaultValue: Readonly<IProductType> = {};
