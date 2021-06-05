export interface ITransaction {
  id?: number;
  transactionid?: string;
  transaction_method?: string;
  paymentId?: number;
}

export const defaultValue: Readonly<ITransaction> = {};
