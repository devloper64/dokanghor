import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import '../form.scss'

import {getEntity, updateEntity, createEntity, reset} from './shipping-address.reducer';
import {IShippingAddress} from 'app/shared/model/shipping-address.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import {getUsers} from "app/modules/administration/user-management/user-management.reducer";

export interface IShippingAddressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ShippingAddressUpdate = (props: IShippingAddressUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {shippingAddressEntity, loading, updating,users} = props;

  const handleClose = () => {
    props.history.push('/shipping-address');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...shippingAddressEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Create Or Update ShippingAddress</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : shippingAddressEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="shipping-address-id">ID</Label>
                      <AvInput className="input--style-5"  id="shipping-address-id" type="text"  name="id" required
                               readOnly/>
                    </AvGroup>
                  ) : null}

                  <AvGroup>
                    <Label id="divisionsLabel" for="shipping-address-divisions">
                      Divisions
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="shipping-address-divisions"
                      type="text"
                      name="divisions"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>



                  <AvGroup>
                    <Label id="districtLabel" for="shipping-address-district">
                      District
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="shipping-address-district"
                      type="text"
                      name="district"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="upazilaLabel" for="shipping-address-upazila">
                      Upazila
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="shipping-address-upazila"
                      type="text"
                      name="upazila"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="postalcodeLabel" for="shipping-address-postalcode">
                      Postalcode
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="shipping-address-postalcode"
                      type="text"
                      name="postalcode"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>


                  <AvGroup>
                    <Label id="phoneNumberLabel" for="shipping-address-phoneNumber">
                      Phone
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="shipping-address-phoneNumber"
                      type="text"
                      name="phoneNumber"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>





                  <AvGroup>
                    <Label for="shipping-address-user">User</Label>
                    <AvInput id="shipping-address-user" type="select" className="form-control" name="userId"
                             required>
                      {users
                        ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>


                  <Button tag={Link} id="cancel-save" to="/shipping-address" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/>
                    &nbsp;
                    <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button className="btn btn--radius-2 btn--green" id="save-entity" type="submit" disabled={updating}>
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
  shippingAddressEntity: storeState.shippingAddress.entity,
  loading: storeState.shippingAddress.loading,
  updating: storeState.shippingAddress.updating,
  updateSuccess: storeState.shippingAddress.updateSuccess,
  users: storeState.userManagement.users
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getUsers
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShippingAddressUpdate);
