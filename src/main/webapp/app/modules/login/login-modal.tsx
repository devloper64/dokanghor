import React from 'react';
import './login.scss'
import {Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col} from 'reactstrap';
import {AvForm, AvField, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {Link} from 'react-router-dom';
import 'font-awesome/css/font-awesome.min.css';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: Function;
  handleClose: Function;
}

class LoginModal extends React.Component<ILoginModalProps> {
  handleSubmit = (event, errors, {username, password, rememberMe}) => {
    const {handleLogin} = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const {loginError, handleClose} = this.props;

    return (
      <div className="login-limiter">
        <div className="container-login100">
          <div className="wrap-login100">
            <div className="login100-pic js-tilt" data-tilt>
              <img src="../../../content/images/home.png" alt="IMG"/>
            </div>

            <AvForm className="login100-form" onSubmit={this.handleSubmit}>
              <span className="login100-form-title" id="login-title">
                Sign in
              </span>
              <ModalBody>
                <Row>
                  <Col md="12">
                    {loginError ? (
                      <Alert color="danger">
                        <strong>Failed to sign in!</strong> Please check your credentials and try again.
                      </Alert>
                    ) : null}
                  </Col>
                  <Col md="12">

                    <div className="wrap-input100 validate-input">
                      <AvField
                        className="input100"
                        name="username"
                        placeholder="Your username"
                        required
                        errorMessage="Username cannot be empty!"
                      />
                      <span className="focus-input100"></span>
                      <span className="symbol-input100">
						        	<i className="fa fa-envelope" aria-hidden="true"></i>
					          	</span>
                    </div>


                    <div className="wrap-input100 validate-input" data-validate="Password is required">
                      <AvField
                        className="input100"
                        name="password"
                        type="password"
                        placeholder="Your password"
                        required
                        errorMessage="Password cannot be empty!"
                      />
                        <span className="focus-input100"></span>
                        <span className="symbol-input100">
							         <i className="fa fa-lock" aria-hidden="true"></i>
						          </span>
                    </div>

                    <AvGroup check inline>
                      <Label className="form-check-label">
                        <AvInput type="checkbox" name="rememberMe"/> <span className="login100-form-remember">Remember me</span>
                      </Label>
                    </AvGroup>
                  </Col>
                </Row>
                <div className="mt-1">&nbsp;</div>
                <Alert color="warning">
                  <Link to="/account/reset/request">Did you forget your password?</Link>
                </Alert>
              </ModalBody>
              <ModalFooter>
                <div className="container-login100-form-btn">
                  <Button className="login100-form-btn" type="submit">
                    Sign in
                  </Button>
                </div>

              </ModalFooter>
            </AvForm>
          </div>
        </div>
      </div>
    );
  }
}

export default LoginModal;
