import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {ICrudGetAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './shipping-address.reducer';
import {IShippingAddress} from 'app/shared/model/shipping-address.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IShippingAddressDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ShippingAddressDetail = (props: IShippingAddressDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {shippingAddressEntity} = props;
  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Shipping Address view</h2>
            </div>

            <div className="card-body">
              <Row>
                <Col md="8">
                  <div className="name">Id</div>
                  <b >{shippingAddressEntity.id}</b>
                  <dl className="jh-entity-details">
                    <dt className="m-t">
                      <span id="district">District</span>
                    </dt>
                    <dd>{shippingAddressEntity.district}</dd>
                    <dt className="m-t">
                      <span id="upazila">Upazila</span>
                    </dt>
                    <dd>{shippingAddressEntity.upazila}</dd>
                    <dt className="m-t">
                      <span id="postalcode">Postalcode</span>
                    </dt>
                    <dd>{shippingAddressEntity.postalcode}</dd>
                  </dl>
                  <div className="m-t">
                  <Button tag={Link} to="/shipping-address" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button tag={Link} to={`/shipping-address/${shippingAddressEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({shippingAddress}: IRootState) => ({
  shippingAddressEntity: shippingAddress.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShippingAddressDetail);
