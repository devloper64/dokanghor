import React from 'react';
import { Card } from 'reactstrap';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Settings from './settings/settings';
import Password from './password/password';
import PageNotFound from "app/shared/error/page-not-found";

const Routes = ({ match }) => (
  <Card className="jh-card">
  <div>
    <Switch>
    <ErrorBoundaryRoute path={`${match.url}/settings`} component={Settings} />
    <ErrorBoundaryRoute path={`${match.url}/password`} component={Password} />
    <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
  </Card>
);

export default Routes;
