import { Moment } from 'moment';

export interface IInvoice {
  id?: number;
  invoice_number?: string;
  to?: string;
  item_list?: string;
  subtotal?: number;
  discount?: number;
  vat?: number;
  total?: number;
  invoice_date?: string;
  transactionId?: number;
}

export const defaultValue: Readonly<IInvoice> = {};
