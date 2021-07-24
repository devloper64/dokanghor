export interface IProductDetails {
  id?: number;
  brand?: string;
  color?: string;
  gender?: string;
  style?: string;
  size_mesaurments?: string;
  size_details?: string;
  productId?: number;
}

export const defaultValue: Readonly<IProductDetails> = {};
