import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {ICrudGetAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './product.reducer';
import {IProduct} from 'app/shared/model/product.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ProductDetail = (props: IProductDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {productEntity} = props;
  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Product View</h2>
            </div>

            <div className="card-body">
              <Row>
                <Col md="8">
                  <div className="name">Id</div>
                  <b>{productEntity.id}</b>
                  <dl className="jh-entity-details">
                    <dt className="m-t">
                      <span id="name">Name</span>
                    </dt>
                    <dd>{productEntity.name}</dd>
                    <dt className="m-t">
                      <span id="price">Price</span>
                    </dt>
                    <dd>{productEntity.price}</dd>
                    <dt className="m-t">
                      <span id="name">Image Link</span>
                    </dt>
                    <dd>{productEntity.image}</dd>
                    <dt className="m-t">
                      <span id="name">Image</span>
                    </dt>
                    <dd><img className="img" src={productEntity.image} alt="dokanghor"/></dd>
                    <dt className="m-t">Sub Category</dt>
                    <dd>{productEntity.subCategoryName ? productEntity.subCategoryName : ''}</dd>
                    <dt className="m-t">Product Type</dt>
                    <dd>{productEntity.productTypeId ? productEntity.productTypeId : ''}</dd>
                    <dt className="m-t">Discount Amount</dt>
                    <dd>{productEntity.discount_amount}</dd>
                    <dt className="m-t">Quantity</dt>
                    <dd>{productEntity.quantity}</dd>
                    <dt className="m-t">Product Details</dt>
                    <dd><ul><li>{productEntity.productDetails?productEntity.productDetails.id: null}</li></ul></dd>
                    <dt className="m-t">User</dt>
                    <dd>{productEntity.userId ? productEntity.userId : ''}</dd>
                  </dl>
                  <div className="m-t">
                    <Button tag={Link} to="/product" replace color="info">
                      <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                    </Button>
                    &nbsp;
                    <Button tag={Link} to={`/product/${productEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({product}: IRootState) => ({
  productEntity: product.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetail);
