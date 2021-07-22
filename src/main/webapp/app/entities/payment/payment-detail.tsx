import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col,Badge} from 'reactstrap';
import {ICrudGetAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import '../form.scss'

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './payment.reducer';
import {IPayment} from 'app/shared/model/payment.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IPaymentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const PaymentDetail = (props: IPaymentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {paymentEntity} = props;
  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Payment View</h2>
            </div>

            <div className="card-body">
              <Row>
                <Col md="8">
                  <div className="name">Id</div>
                  <b >{paymentEntity.id}</b>
                  <dl className="jh-entity-details">
                    <dt className="m-t">
                      <span id="amount">Amount</span>
                    </dt>
                    <dd>{paymentEntity.amount}</dd>
                    <dt className="m-t">User</dt>
                    <dd>{paymentEntity.userLogin ? paymentEntity.userLogin : ''}</dd>
                    <dt className="m-t">Products</dt>
                    <dd>
                      <ul className="list-unstyled">
                        {paymentEntity.products
                          ? paymentEntity.products.map((product, i) => (
                            <li key={`user-auth-${i}`}>
                              <Badge color="info">{product.name}</Badge>
                            </li>
                          ))
                          : null}
                      </ul>
                    </dd>
                    <dt className="m-t">Shipping Address</dt>
                    <dd>{paymentEntity.shippingAddressId ? paymentEntity.shippingAddressId : ''}</dd>
                  </dl>
                  <div className="m-t">
                  <Button tag={Link} to="/payment" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
                    <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                  </Button>
                  </div>
                </Col>
              </Row>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({payment}: IRootState) => ({
  paymentEntity: payment.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentDetail);
