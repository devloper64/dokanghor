import React, {useState, useEffect} from 'react';

import {connect} from 'react-redux';
import {AvForm, AvField} from 'availity-reactstrap-validation';
import {Row, Col, Button} from 'reactstrap';
import '../../../entities/form.scss'

import {IRootState} from 'app/shared/reducers';
import {getSession} from 'app/shared/reducers/authentication';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import {savePassword, reset} from './password.reducer';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export interface IUserPasswordProps extends StateProps, DispatchProps {
}

export const PasswordPage = (props: IUserPasswordProps) => {
  const [password, setPassword] = useState('');

  useEffect(() => {
    props.reset();
    props.getSession();
    return () => {
      props.reset();
    };
  }, []);

  const handleValidSubmit = (event, values) => {
    props.savePassword(values.currentPassword, values.newPassword);
  };

  const updatePassword = event => setPassword(event.target.value);

  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Change Password for {props.account.login}</h2>
            </div>

            <div className="card-body">
              <AvForm id="password-form" onValidSubmit={handleValidSubmit}>
                <AvField
                  className="input--style-5"
                  name="currentPassword"
                  label="Current password"
                  placeholder={'Current password'}
                  type="password"
                  validate={{
                    required: {value: true, errorMessage: 'Your password is required.'},
                  }}
                />
                <AvField
                  className="input--style-5"
                  name="newPassword"
                  label="New password"
                  placeholder={'New password'}
                  type="password"
                  validate={{
                    required: {value: true, errorMessage: 'Your password is required.'},
                    minLength: {value: 4, errorMessage: 'Your password is required to be at least 4 characters.'},
                    maxLength: {value: 50, errorMessage: 'Your password cannot be longer than 50 characters.'},
                  }}
                  onChange={updatePassword}
                />
                <PasswordStrengthBar password={password}/>
                <AvField
                  className="input--style-5"
                  name="confirmPassword"
                  label="New password confirmation"
                  placeholder="Confirm the new password"
                  type="password"
                  validate={{
                    required: {
                      value: true,
                      errorMessage: 'Your confirmation password is required.',
                    },
                    minLength: {
                      value: 4,
                      errorMessage: 'Your confirmation password is required to be at least 4 characters.',
                    },
                    maxLength: {
                      value: 50,
                      errorMessage: 'Your confirmation password cannot be longer than 50 characters.',
                    },
                    match: {
                      value: 'newPassword',
                      errorMessage: 'The password and its confirmation do not match!',
                    },
                  }}
                />
                <Button className="btn btn--radius-2 btn--green" type="submit">
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({authentication}: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
});

const mapDispatchToProps = {getSession, savePassword, reset};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PasswordPage);
