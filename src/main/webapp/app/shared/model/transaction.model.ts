export interface ITransaction {
  id?: number;
  transactionid?: string;
  paymentId?: number;
  transactionMethodId?: number;
  is_transaction_completed?: boolean;
}

export const defaultValue: Readonly<ITransaction> = {};
