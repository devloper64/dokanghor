import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShippingAddress from './shipping-address';
import ShippingAddressDetail from './shipping-address-detail';
import ShippingAddressUpdate from './shipping-address-update';
import ShippingAddressDeleteDialog from './shipping-address-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShippingAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShippingAddressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShippingAddressDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShippingAddress} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ShippingAddressDeleteDialog} />
  </>
);

export default Routes;
