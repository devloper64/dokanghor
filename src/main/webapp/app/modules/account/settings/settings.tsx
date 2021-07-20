import React, {useEffect} from 'react';
import {Button, Col, Alert, Row} from 'reactstrap';
import {connect} from 'react-redux';
import '../../../entities/form.scss'
import {AvForm, AvField} from 'availity-reactstrap-validation';

import {IRootState} from 'app/shared/reducers';
import {getSession} from 'app/shared/reducers/authentication';
import {saveAccountSettings, reset} from './settings.reducer';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

export interface IUserSettingsProps extends StateProps, DispatchProps {
}

export const SettingsPage = (props: IUserSettingsProps) => {
  useEffect(() => {
    props.getSession();
    return () => {
      props.reset();
    };
  }, []);

  const handleValidSubmit = (event, values) => {
    const account = {
      ...props.account,
      ...values,
    };

    props.saveAccountSettings(account);
    event.persist();
  };

  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Settings for {props.account.login}</h2>
            </div>

            <div className="card-body">
              <AvForm id="settings-form" onValidSubmit={handleValidSubmit}>
                {/* First name */}
                <AvField

                  className="input--style-5"
                  name="firstName"
                  label="First Name"
                  id="firstName"
                  placeholder="Your first name"
                  validate={{
                    required: {value: true, errorMessage: 'Your first name is required.'},
                    minLength: {value: 1, errorMessage: 'Your first name is required to be at least 1 character'},
                    maxLength: {value: 50, errorMessage: 'Your first name cannot be longer than 50 characters'},
                  }}
                  value={props.account.firstName}
                />
                {/* Last name */}
                <AvField
                  className="input--style-5"
                  name="lastName"
                  label="Last Name"
                  id="lastName"
                  placeholder="Your last name"
                  validate={{
                    required: {value: true, errorMessage: 'Your last name is required.'},
                    minLength: {value: 1, errorMessage: 'Your last name is required to be at least 1 character'},
                    maxLength: {value: 50, errorMessage: 'Your last name cannot be longer than 50 characters'},
                  }}
                  value={props.account.lastName}
                />
                {/* Email */}
                <AvField
                  className="input--style-5"
                  name="email"
                  label="Email"
                  placeholder={'Your email'}
                  type="email"
                  validate={{
                    required: {value: true, errorMessage: 'Your email is required.'},
                    minLength: {value: 5, errorMessage: 'Your email is required to be at least 5 characters.'},
                    maxLength: {value: 254, errorMessage: 'Your email cannot be longer than 50 characters.'},
                  }}
                  value={props.account.email}
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

const mapDispatchToProps = {getSession, saveAccountSettings, reset};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
