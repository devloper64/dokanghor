import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProductImages from './product-images';
import ProductImagesDetail from './product-images-detail';
import ProductImagesUpdate from './product-images-update';
import ProductImagesDeleteDialog from './product-images-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProductImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProductImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProductImagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProductImages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProductImagesDeleteDialog} />
  </>
);

export default Routes;
