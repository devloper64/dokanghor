import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderStatus from './order-status';
import OrderStatusDetail from './order-status-detail';
import OrderStatusUpdate from './order-status-update';
import OrderStatusDeleteDialog from './order-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderStatus} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrderStatusDeleteDialog} />
  </>
);

export default Routes;
