import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-details.reducer';
import { IProductDetails } from 'app/shared/model/product-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductDetailsDetail = (props: IProductDetailsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productDetailsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ProductDetails [<b>{productDetailsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="brand">Brand</span>
          </dt>
          <dd>{productDetailsEntity.brand}</dd>
          <dt>
            <span id="color">Color</span>
          </dt>
          <dd>{productDetailsEntity.color}</dd>
          <dt>
            <span id="gender">Gender</span>
          </dt>
          <dd>{productDetailsEntity.gender}</dd>
          <dt>
            <span id="style">Style</span>
          </dt>
          <dd>{productDetailsEntity.style}</dd>
          <dt>
            <span id="size_mesaurments">Size Mesaurments</span>
          </dt>
          <dd>{productDetailsEntity.size_mesaurments}</dd>
          <dt>
            <span id="size_details">Size Details</span>
          </dt>
          <dd>{productDetailsEntity.size_details}</dd>
        </dl>
        <Button tag={Link} to="/product-details" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-details/${productDetailsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ productDetails }: IRootState) => ({
  productDetailsEntity: productDetails.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetailsDetail);
