import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {ICrudGetAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import '../form.scss'

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './product-details.reducer';
import {IProductDetails} from 'app/shared/model/product-details.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IProductDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ProductDetailsDetail = (props: IProductDetailsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {productDetailsEntity} = props;
  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Address View</h2>
            </div>

            <div className="card-body">
              <Row>
                <Col md="8">
                  <div className="name">Id</div>
                  <b >{productDetailsEntity.id}</b>
                  <dl className="jh-entity-details">
                    <dt className="m-t">
                      <span id="brand">Brand</span>
                    </dt>
                    <dd>{productDetailsEntity.brand}</dd>
                    <dt className="m-t">
                      <span id="color">Color</span>
                    </dt>
                    <dd>{productDetailsEntity.color}</dd>
                   <dt className="m-t">
                      <span id="gender">Gender</span>
                    </dt>
                    <dd>{productDetailsEntity.gender}</dd>
                   <dt className="m-t">
                      <span id="style">Style</span>
                    </dt>
                    <dd>{productDetailsEntity.style}</dd>
                   <dt className="m-t">
                      <span id="size_mesaurments">Size Mesaurments</span>
                    </dt>
                    <dd>{productDetailsEntity.size_mesaurments}</dd>
                   <dt className="m-t">
                      <span id="size_details">Size Details</span>
                    </dt>
                    <dd>{productDetailsEntity.size_details}</dd>
                  </dl>
                  <div className="m-t">
                  <Button tag={Link} to="/product-details" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button tag={Link} to={`/product-details/${productDetailsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({productDetails}: IRootState) => ({
  productDetailsEntity: productDetails.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetailsDetail);
