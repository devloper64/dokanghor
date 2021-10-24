import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Divisions from './divisions';
import DivisionsDetail from './divisions-detail';
import DivisionsUpdate from './divisions-update';
import DivisionsDeleteDialog from './divisions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DivisionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DivisionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DivisionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Divisions} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DivisionsDeleteDialog} />
  </>
);

export default Routes;
