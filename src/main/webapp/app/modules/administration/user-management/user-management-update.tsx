import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Label, Row, Col} from 'reactstrap';
import {AvForm, AvGroup, AvInput, AvField, AvFeedback} from 'availity-reactstrap-validation';
import '../../../entities/form.scss'

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {getUser, getRoles, updateUser, createUser, reset} from './user-management.reducer';
import {IRootState} from 'app/shared/reducers';

export interface IUserManagementUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ login: string }> {
}

export const UserManagementUpdate = (props: IUserManagementUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.login);

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getUser(props.match.params.login);
    }
    props.getRoles();
    return () => {
      props.reset();
    };
  }, []);

  const handleClose = () => {
    props.history.push('/admin/user-management');
  };

  const saveUser = (event, values) => {
    if (isNew) {
      props.createUser(values);
    } else {
      props.updateUser(values);
    }
    handleClose();
  };

  const isInvalid = false;
  const {user, loading, updating, roles} = props;

  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Create Or Update User</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm onValidSubmit={saveUser}>
                  {user.id ? (
                    <AvGroup>
                      <Label for="id">ID</Label>
                      <AvField type="text" className="input--style-5" name="id" required readOnly value={user.id}/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label for="login">Login</Label>
                    <AvField
                      type="text"
                      className="input--style-5"
                      name="login"
                      validate={{
                        required: {
                          value: true,
                          errorMessage: 'Your username is required.',
                        },
                        pattern: {
                          value: '^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$',
                          errorMessage: 'Your username is invalid.',
                        },
                        minLength: {
                          value: 1,
                          errorMessage: 'Your username is required to be at least 1 character.',
                        },
                        maxLength: {
                          value: 50,
                          errorMessage: 'Your username cannot be longer than 50 characters.',
                        },
                      }}
                      value={user.login}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label for="firstName">First Name</Label>
                    <AvField
                      type="text"
                      className="input--style-5"
                      name="firstName"
                      validate={{
                        maxLength: {
                          value: 50,
                          errorMessage: 'This field cannot be longer than 50 characters.',
                        },
                      }}
                      value={user.firstName}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label for="lastName">Last Name</Label>
                    <AvField
                      type="text"
                      className="input--style-5"
                      name="lastName"
                      validate={{
                        maxLength: {
                          value: 50,
                          errorMessage: 'This field cannot be longer than 50 characters.',
                        },
                      }}
                      value={user.lastName}
                    />
                    <AvFeedback>This field cannot be longer than 50 characters.</AvFeedback>
                  </AvGroup>
                  <AvGroup>
                    <AvField
                      className="input--style-5"
                      name="email"
                      label="Email"
                      placeholder={'Your email'}
                      type="email"
                      validate={{
                        required: {
                          value: true,
                          errorMessage: 'Your email is required.',
                        },
                        email: {
                          errorMessage: 'Your email is invalid.',
                        },
                        minLength: {
                          value: 5,
                          errorMessage: 'Your email is required to be at least 5 characters.',
                        },
                        maxLength: {
                          value: 254,
                          errorMessage: 'Your email cannot be longer than 50 characters.',
                        },
                      }}
                      value={user.email}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label for="phone">Phone</Label>
                    <AvField
                      type="text"
                      className="input--style-5"
                      name="phone"
                      validate={{
                        maxLength: {
                          value: 15,
                          errorMessage: 'This field cannot be longer than 15 characters.',
                        },
                      }}
                      value={user.phone}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label for="fcmToken">Fcm Token</Label>
                    <AvField
                      type="text"
                      className="input--style-5"
                      name="fcmToken"
                      value={user.fcmToken}
                    />
                  </AvGroup>

                  <AvGroup check>
                    <Label className="m-t m-l" for="activated">
                      <AvInput   className="checkmark" type="checkbox" name="activated" value={user.activated} checked={user.activated}
                               disabled={!user.id}/> Activated
                    </Label>
                  </AvGroup>
                  <AvGroup>
                    <Label for="authorities">Profiles</Label>
                    <AvInput type="select" className="form-control" name="authorities" value={user.authorities}
                             multiple>
                      {roles.map(role => (
                        <option value={role} key={role}>
                          {role}
                        </option>
                      ))}
                    </AvInput>
                  </AvGroup>
                  <Button tag={Link} to="/admin/user-management" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/>
                    &nbsp;
                    <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button  className="btn btn--radius-2 btn--green" type="submit" disabled={isInvalid || updating}>
                    <FontAwesomeIcon icon="save"/>
                    &nbsp; Save
                  </Button>
                </AvForm>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
  roles: storeState.userManagement.authorities,
  loading: storeState.userManagement.loading,
  updating: storeState.userManagement.updating,
});

const mapDispatchToProps = {getUser, getRoles, updateUser, createUser, reset};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserManagementUpdate);
