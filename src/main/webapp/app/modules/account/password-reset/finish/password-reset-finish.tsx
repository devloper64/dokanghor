import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Col, Row, Button } from 'reactstrap';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { getUrlParameter } from 'react-jhipster';
import { RouteComponentProps } from 'react-router-dom';
import '../../../../entities/form.scss'

import { handlePasswordResetFinish, reset } from '../password-reset.reducer';
import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';

export interface IPasswordResetFinishProps extends DispatchProps, RouteComponentProps<{ key: string }> {}

export const PasswordResetFinishPage = (props: IPasswordResetFinishProps) => {
  const [password, setPassword] = useState('');
  const [key] = useState(getUrlParameter('key', props.location.search));

  useEffect(
    () => () => {
      props.reset();
    },
    []
  );

  const handleValidSubmit = (event, values) => props.handlePasswordResetFinish(key, values.newPassword);

  const updatePassword = event => setPassword(event.target.value);

  const getResetForm = () => {
    return (
      <div className="entity-form">
        <div className="page-wrapper  p-t-45 p-b-50">
          <div className="wrapper wrapper--w790">
            <div className="card-5">
              <div className="card-heading">
                <h2 className="title">Reset Password</h2>
              </div>
              <div className="card-body">
                <AvForm onValidSubmit={handleValidSubmit}>
                  <AvField
                    className="input--style-5"
                    name="newPassword"
                    label="New password"
                    placeholder={'New password'}
                    type="password"
                    validate={{
                      required: { value: true, errorMessage: 'Your password is required.' },
                      minLength: { value: 4, errorMessage: 'Your password is required to be at least 4 characters.' },
                      maxLength: { value: 50, errorMessage: 'Your password cannot be longer than 50 characters.' },
                    }}
                    onChange={updatePassword}
                  />
                  <PasswordStrengthBar password={password} />
                  <AvField
                    className="input--style-5"
                    name="confirmPassword"
                    label="New password confirmation"
                    placeholder="Confirm the new password"
                    type="password"
                    validate={{
                      required: { value: true, errorMessage: 'Your confirmation password is required.' },
                      minLength: { value: 4, errorMessage: 'Your confirmation password is required to be at least 4 characters.' },
                      maxLength: { value: 50, errorMessage: 'Your confirmation password cannot be longer than 50 characters.' },
                      match: { value: 'newPassword', errorMessage: 'The password and its confirmation do not match!' },
                    }}
                  />
                  <Button className="btn btn--radius-2 btn--green" type="submit">
                    Validate new password
                  </Button>
                </AvForm>
              </div>
            </div>
          </div>
        </div>
      </div>

    );
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="4">
          <div>{key ? getResetForm() : null}</div>
        </Col>
      </Row>
    </div>
  );
};

const mapDispatchToProps = { handlePasswordResetFinish, reset };

type DispatchProps = typeof mapDispatchToProps;

export default connect(null, mapDispatchToProps)(PasswordResetFinishPage);
