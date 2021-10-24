import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Upazilas from './upazilas';
import UpazilasDetail from './upazilas-detail';
import UpazilasUpdate from './upazilas-update';
import UpazilasDeleteDialog from './upazilas-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UpazilasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UpazilasUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UpazilasDetail} />
      <ErrorBoundaryRoute path={match.url} component={Upazilas} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UpazilasDeleteDialog} />
  </>
);

export default Routes;
