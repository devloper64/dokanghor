import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import category, {
  CategoryState
} from 'app/entities/category/category.reducer';
// prettier-ignore
import subCategory, {
  SubCategoryState
} from 'app/entities/sub-category/sub-category.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product.reducer';
// prettier-ignore
import shippingAddress, {
  ShippingAddressState
} from 'app/entities/shipping-address/shipping-address.reducer';
// prettier-ignore
import payment, {
  PaymentState
} from 'app/entities/payment/payment.reducer';
// prettier-ignore
import transaction, {
  TransactionState
} from 'app/entities/transaction/transaction.reducer';
// prettier-ignore
import mobileIntro, {
  MobileIntroState
} from 'app/entities/mobile-intro/mobile-intro.reducer';
// prettier-ignore
import productImages, {
  ProductImagesState
} from 'app/entities/product-images/product-images.reducer';
// prettier-ignore
import productType, {
  ProductTypeState
} from 'app/entities/product-type/product-type.reducer';
// prettier-ignore
import productDetails, {
  ProductDetailsState
} from 'app/entities/product-details/product-details.reducer';
// prettier-ignore
import orderStatus, {
  OrderStatusState
} from 'app/entities/order-status/order-status.reducer';
// prettier-ignore
import transactionMethod, {
  TransactionMethodState
} from 'app/entities/transaction-method/transaction-method.reducer';
// prettier-ignore
import invoice, {
  InvoiceState
} from 'app/entities/invoice/invoice.reducer';
// prettier-ignore
import divisions, {
  DivisionsState
} from 'app/entities/divisions/divisions.reducer';
// prettier-ignore
import districts, {
  DistrictsState
} from 'app/entities/districts/districts.reducer';
// prettier-ignore
import upazilas, {
  UpazilasState
} from 'app/entities/upazilas/upazilas.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly address: AddressState;
  readonly category: CategoryState;
  readonly subCategory: SubCategoryState;
  readonly product: ProductState;
  readonly shippingAddress: ShippingAddressState;
  readonly payment: PaymentState;
  readonly transaction: TransactionState;
  readonly mobileIntro: MobileIntroState;
  readonly productImages: ProductImagesState;
  readonly productType: ProductTypeState;
  readonly productDetails: ProductDetailsState;
  readonly orderStatus: OrderStatusState;
  readonly transactionMethod: TransactionMethodState;
  readonly invoice: InvoiceState;
  readonly divisions: DivisionsState;
  readonly districts: DistrictsState;
  readonly upazilas: UpazilasState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  address,
  category,
  subCategory,
  product,
  shippingAddress,
  payment,
  transaction,
  mobileIntro,
  productImages,
  productType,
  productDetails,
  orderStatus,
  transactionMethod,
  invoice,
  divisions,
  districts,
  upazilas,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
