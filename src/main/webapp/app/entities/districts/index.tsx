import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Districts from './districts';
import DistrictsDetail from './districts-detail';
import DistrictsUpdate from './districts-update';
import DistrictsDeleteDialog from './districts-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DistrictsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DistrictsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DistrictsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Districts} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DistrictsDeleteDialog} />
  </>
);

export default Routes;
