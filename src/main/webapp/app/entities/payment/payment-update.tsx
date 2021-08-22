import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import '../form.scss'

import {IUser} from 'app/shared/model/user.model';
import {getUsers} from 'app/modules/administration/user-management/user-management.reducer';
import {IProduct} from 'app/shared/model/product.model';
import {getEntities as getProducts} from 'app/entities/product/product.reducer';
import {IShippingAddress} from 'app/shared/model/shipping-address.model';
import {getEntities as getShippingAddresses} from 'app/entities/shipping-address/shipping-address.reducer';

import {getEntities as getOrderStatus} from 'app/entities/order-status/order-status.reducer';

import {getEntity, updateEntity, createEntity, reset} from './payment.reducer';
import {IPayment} from 'app/shared/model/payment.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';

export interface IPaymentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const PaymentUpdate = (props: IPaymentUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [productId, setProductId] = useState('0');
  const [shippingAddressId, setShippingAddressId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {paymentEntity, users, products, shippingAddresses,orderStatus, loading, updating} = props;

  const [productAmountList, setProductAmountList] = useState([{productId: "",amount:""}]);
  const [readProductAmount, setReadProductAmount] = useState(false);


  const [productQuantityList, setProductQuantityList] = useState([{productId: "",quantity:""}]);
  const [readProductQuantity, setReadProductQuantity] = useState(false);



  // -------- product amount----------
  const handleProductAmount = (e, index) => {
    const {name, value} = e.target;
    const list = [...productAmountList];
    list[index][name] = value;
    setProductAmountList(list);
  };

  // handle click event of the Remove button
  const handleProductAmountRemoveClick = index => {
    const list = [...productAmountList];
    list.splice(index, 1);
    setProductAmountList(list);
  };

  // handle click event of the Add button
  const handleProductAmountAddClick = () => {
    setProductAmountList([...productAmountList, {productId: "",amount:""}]);
  };

  const initialProductAmount = (value) => {
    const jsonObj =JSON.parse(value);
    jsonObj.map((v,i)=>{
      if (v!==""){
        productAmountList.push(v)
      }

    })

  }

  const initialAmountData = (value) => {

    initialProductAmount(value)
    setReadProductAmount(true)
  }


  // -------- product quantity----------
  const handleProductQuantity = (e, index) => {
    const {name, value} = e.target;
    const list = [...productQuantityList];
    list[index][name] = value;
    setProductQuantityList(list);
  };

  // handle click event of the Remove button
  const handleProductQuantityRemoveClick = index => {
    const list = [...productQuantityList];
    list.splice(index, 1);
    setProductQuantityList(list);
  };

  // handle click event of the Add button
  const handleProductQuantityAddClick = () => {
    setProductQuantityList([...productQuantityList, {productId: "",quantity:""}]);
  };

  const initialProductQuantity = (value) => {
    const jsonObj =JSON.parse(value);
    jsonObj.map((v,i)=>{
      if (v!==""){
        productQuantityList.push(v)
      }

    })

  }

  const initialQuantityData = (value) => {

    initialProductQuantity(value)
    setReadProductQuantity(true)
  }

  // -----------------

  const handleClose = () => {
    props.history.push('/payment');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
      setProductAmountList([])
      setProductQuantityList([])
    }

    props.getUsers();
    props.getProducts();
    props.getShippingAddresses();
    props.getOrderStatus();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...paymentEntity,
        ...values,
        products:mapIdList(values.products)
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
              <h2 className="title">Create Or Update Payment</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : paymentEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="payment-id">ID</Label>
                      <AvInput id="payment-id" type="text" className="input--style-5" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="amountLabel" for="payment-amount">
                      Total Amount
                    </Label>
                    <AvField
                      id="payment-amount"
                      type="string"
                      className="input--style-5"
                      name="totalAmount"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                        number: {value: true, errorMessage: 'This field should be a number.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label for="payment-user">User</Label>
                    <AvInput id="payment-user" type="select" className="form-control" name="userId" required>
                      {users
                        ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>
                  <AvGroup>
                    <Label for="payment-product">Products</Label>
                    <AvInput id="payment-product"
                             type="select"
                             className="form-control"
                             name="products"
                             value={paymentEntity.products && paymentEntity.products.map(e => e.id)} multiple>
                      {products?
                        products.map(product => (
                          <option value={product.id} key={product.id}>
                            {product.name}
                          </option>
                        ))
                        :null
                      }
                    </AvInput>
                  </AvGroup>



                  {
                    paymentEntity.individualAmount!=null && !isNew && !readProductAmount?initialAmountData(paymentEntity.individualAmount):null
                  }

                  {productAmountList.map((x, i) => (
                    <AvGroup key="">
                      <Label id="colorLabel" for="product-details-color">
                        Add individual Amount
                      </Label>

                      <AvInput
                        id="payment-pid"
                        type="select"
                        className="form-control"
                        value={x.productId}
                        onChange={e => handleProductAmount(e, i)}
                        name="productId" required>

                        {products
                          ? products.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.name}
                            </option>
                          ))
                          : null}
                      </AvInput>

                      <AvField
                        key=""
                        placeholder="amount"
                        className="m-t input--style-multiple"
                        id="payment-amount"
                        type="text"
                        value={x.amount}
                        onChange={e => handleProductAmount(e, i)}
                        name="amount"/>

                      <div>
                        {productAmountList.length !== 1 && (
                          <Button
                            className="m-r btn btn--radius-2 btn--red"
                            onClick={() => handleProductAmountRemoveClick(i)}
                          >
                            Remove
                          </Button>
                        )}
                        {productAmountList.length - 1 === i && (
                          <Button className="btn btn--radius-2 btn--green" onClick={handleProductAmountAddClick}>Add</Button>
                        )}
                      </div>
                    </AvGroup>
                  ))}





                  {
                    paymentEntity.productQuantities!=null && !isNew && !readProductQuantity?initialQuantityData(paymentEntity.productQuantities):null
                  }

                  {productQuantityList.map((x, i) => (
                    <AvGroup key="">
                      <Label id="colorLabel" for="product-details-color">
                        Add individual Quantity
                      </Label>

                      <AvInput
                        id="payment-pid"
                        type="select"
                        className="form-control"
                        value={x.productId}
                        onChange={e => handleProductQuantity(e, i)}
                        name="productId" required>

                        {products
                          ? products.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.name}
                            </option>
                          ))
                          : null}
                      </AvInput>

                      <AvField
                        key=""
                        placeholder="quantity"
                        className="m-t input--style-multiple"
                        id="payment-quantity"
                        type="text"
                        value={x.quantity}
                        onChange={e => handleProductQuantity(e, i)}
                        name="quantity"/>

                      <div>
                        {productQuantityList.length !== 1 && (
                          <Button
                            className="m-r btn btn--radius-2 btn--red"
                            onClick={() => handleProductQuantityRemoveClick(i)}
                          >
                            Remove
                          </Button>
                        )}
                        {productQuantityList.length - 1 === i && (
                          <Button className="btn btn--radius-2 btn--green" onClick={handleProductQuantityAddClick}>Add</Button>
                        )}
                      </div>
                    </AvGroup>
                  ))}



                  <AvGroup>
                    <Label for="payment-shippingAddress">Shipping Address</Label>
                    <AvInput id="payment-shippingAddress" type="select" className="form-control"
                             name="shippingAddressId" required>

                      {shippingAddresses
                        ? shippingAddresses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>



                  <AvGroup>
                    <Label for="payment-order-status">Order Status</Label>
                    <AvInput id="payment-order-status" type="select" className="form-control"
                             name="orderStatusId" required>

                      {orderStatus
                        ? orderStatus.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>




                  <AvGroup>
                    <AvField className="input--style-5" id="payment-productQuantities" value={JSON.stringify(productQuantityList)} type="hidden"
                             name="productQuantities"/>
                  </AvGroup>

                  <AvGroup>
                    <AvField className="input--style-5" id="payment-individualAmount" value={JSON.stringify(productAmountList)} type="hidden"
                             name="individualAmount"/>
                  </AvGroup>


                  <Button tag={Link} id="cancel-save" to="/payment" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/>
                    &nbsp;
                    <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button className="btn btn--radius-2 btn--green"  id="save-entity" type="submit" disabled={updating}>
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
  users: storeState.userManagement.users,
  products: storeState.product.entities,
  shippingAddresses: storeState.shippingAddress.entities,
  orderStatus: storeState.orderStatus.entities,
  paymentEntity: storeState.payment.entity,
  loading: storeState.payment.loading,
  updating: storeState.payment.updating,
  updateSuccess: storeState.payment.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getProducts,
  getShippingAddresses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  getOrderStatus
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentUpdate);
