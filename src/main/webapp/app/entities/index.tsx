import React from 'react';
import { Switch } from 'react-router-dom';
import { Card } from 'reactstrap';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Address from './address';
import Category from './category';
import SubCategory from './sub-category';
import Product from './product';
import ShippingAddress from './shipping-address';
import Payment from './payment';
import Transaction from './transaction';
import MobileIntro from './mobile-intro';
import ProductImages from './product-images';
import PageNotFound from 'app/shared/error/page-not-found';
import ProductType from './product-type';
import ProductDetails from './product-details';
import OrderStatus from './order-status';
import TransactionMethod from './transaction-method';
import Invoice from './invoice';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}sub-category`} component={SubCategory} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}shipping-address`} component={ShippingAddress} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      <ErrorBoundaryRoute path={`${match.url}transaction`} component={Transaction} />
      <ErrorBoundaryRoute path={`${match.url}mobile-intro`} component={MobileIntro} />
      <ErrorBoundaryRoute path={`${match.url}product-images`} component={ProductImages} />
      <ErrorBoundaryRoute path={`${match.url}product-type`} component={ProductType} />
      <ErrorBoundaryRoute path={`${match.url}product-details`} component={ProductDetails} />
      <ErrorBoundaryRoute path={`${match.url}order-status`} component={OrderStatus} />
      <ErrorBoundaryRoute path={`${match.url}transaction-method`} component={TransactionMethod} />
      <ErrorBoundaryRoute path={`${match.url}invoice`} component={Invoice} />

      <ErrorBoundaryRoute component={PageNotFound} />

      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
