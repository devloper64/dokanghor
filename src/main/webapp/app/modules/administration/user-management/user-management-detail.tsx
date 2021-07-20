import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Badge} from 'reactstrap';
import {TextFormat} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import '../../../entities/form.scss'

import {APP_DATE_FORMAT} from 'app/config/constants';

import {getUser} from './user-management.reducer';
import {IRootState} from 'app/shared/reducers';

export interface IUserManagementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ login: string }> {
}

export const UserManagementDetail = (props: IUserManagementDetailProps) => {
  useEffect(() => {
    props.getUser(props.match.params.login);
  }, []);

  const {user} = props;

  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Address View</h2>
            </div>

            <div className="card-body">
              <Row size="md">
                <dl className="jh-entity-details">
                  <h2>
                    User [<b>{user.login}</b>]
                  </h2>
                  <dt className="m-t">Login</dt>
                  <dd>
                    <span>{user.login}</span>&nbsp;
                    {user.activated ? <Badge color="success">Activated</Badge> :
                      <Badge color="danger">Deactivated</Badge>}
                  </dd>
                  <dt className="m-t">First Name</dt>
                  <dd>{user.firstName}</dd>
                  <dt className="m-t">Last Name</dt>
                  <dd>{user.lastName}</dd>
                  <dt className="m-t">Email</dt>
                  <dd>{user.email}</dd>
                  <dt className="m-t">Created By</dt>
                  <dd>{user.createdBy}</dd>
                  <dt className="m-t">Created Date</dt>
                  <dd>{user.createdDate ? <TextFormat value={user.createdDate} type="date" format={APP_DATE_FORMAT}
                                                      blankOnInvalid/> : null}</dd>
                  <dt className="m-t">Last Modified By</dt>
                  <dd>{user.lastModifiedBy}</dd>
                  <dt className="m-t">Last Modified Date</dt>
                  <dd>
                    {user.lastModifiedDate ? (
                      <TextFormat value={user.lastModifiedDate} type="date" format={APP_DATE_FORMAT} blankOnInvalid/>
                    ) : null}
                  </dd>
                  <dt className="m-t">Profiles</dt>
                  <dd>
                    <ul className="list-unstyled">
                      {user.authorities
                        ? user.authorities.map((authority, i) => (
                          <li key={`user-auth-${i}`}>
                            <Badge color="info">{authority}</Badge>
                          </li>
                        ))
                        : null}
                    </ul>
                  </dd>
                  <div className="m-t">
                    <Button tag={Link} to="/admin/user-management" replace color="info">
                      <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                    </Button>
                  </div>
                </dl>
              </Row>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
});

const mapDispatchToProps = {getUser};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserManagementDetail);
