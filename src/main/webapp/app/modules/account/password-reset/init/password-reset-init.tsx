import React from 'react';

import { connect } from 'react-redux';
import { AvForm, AvField } from 'availity-reactstrap-validation';
import { Button, Alert, Col, Row } from 'reactstrap';
import '../../../../entities/form.scss'

import { IRootState } from 'app/shared/reducers';
import { handlePasswordResetInit, reset } from '../password-reset.reducer';

export type IPasswordResetInitProps = DispatchProps;

export class PasswordResetInit extends React.Component<IPasswordResetInitProps> {
  componentWillUnmount() {
    this.props.reset();
  }

  handleValidSubmit = (event, values) => {
    this.props.handlePasswordResetInit(values.email);
    event.preventDefault();
  };

  render() {
    return (
      <div className="entity-form">
        <div className="page-wrapper  p-t-45 p-b-50">
          <div className="wrapper wrapper--w790">
            <div className="card-5">
              <div className="card-heading">
                <h2 className="title">Reset Password</h2>
              </div>
              <div className="card-body">
                <Alert color="warning">
                  <p>Enter the email address you used to register</p>
                </Alert>
                <AvForm onValidSubmit={this.handleValidSubmit}>
                  <AvField
                    className="input--style-5"
                    name="email"
                    label="Email"
                    placeholder={'Your email'}
                    type="email"
                    validate={{
                      required: { value: true, errorMessage: 'Your email is required.' },
                      minLength: { value: 5, errorMessage: 'Your email is required to be at least 5 characters.' },
                      maxLength: { value: 254, errorMessage: 'Your email cannot be longer than 50 characters.' },
                    }}
                  />
                  <Button className="btn btn--radius-2 btn--blue" type="submit">
                    Reset password
                  </Button>
                </AvForm>
              </div>
            </div>
          </div>
        </div>
      </div>


    );
  }
}

const mapDispatchToProps = { handlePasswordResetInit, reset };

type DispatchProps = typeof mapDispatchToProps;

export default connect(null, mapDispatchToProps)(PasswordResetInit);
