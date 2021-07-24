import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductDetails from './product-details';
import ProductDetailsDetail from './product-details-detail';
import ProductDetailsUpdate from './product-details-update';
import ProductDetailsDeleteDialog from './product-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductDetailsDeleteDialog} />
  </>
);

export default Routes;
