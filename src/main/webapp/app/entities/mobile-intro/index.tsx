import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MobileIntro from './mobile-intro';
import MobileIntroDetail from './mobile-intro-detail';
import MobileIntroUpdate from './mobile-intro-update';
import MobileIntroDeleteDialog from './mobile-intro-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MobileIntroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MobileIntroUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MobileIntroDetail} />
      <ErrorBoundaryRoute path={match.url} component={MobileIntro} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MobileIntroDeleteDialog} />
  </>
);

export default Routes;
