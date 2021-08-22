import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TransactionMethod from './transaction-method';
import TransactionMethodDetail from './transaction-method-detail';
import TransactionMethodUpdate from './transaction-method-update';
import TransactionMethodDeleteDialog from './transaction-method-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TransactionMethodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TransactionMethodUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TransactionMethodDetail} />
      <ErrorBoundaryRoute path={match.url} component={TransactionMethod} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TransactionMethodDeleteDialog} />
  </>
);

export default Routes;
