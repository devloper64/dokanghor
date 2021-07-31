import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';

import {IProduct} from 'app/shared/model/product.model';
import {getEntities as getProducts} from 'app/entities/product/product.reducer';
import {getEntity, updateEntity, createEntity, reset} from './product-details.reducer';
import {IProductDetails} from 'app/shared/model/product-details.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import '../form.scss'

export interface IProductDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ProductDetailsUpdate = (props: IProductDetailsUpdateProps) => {
  const [productId, setProductId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {productDetailsEntity, products, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/product-details');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getProducts();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productDetailsEntity,
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
              <h2 className="title">Create Or Update Product Details</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : productDetailsEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="product-details-id">ID</Label>
                      <AvInput className="input--style-5" id="product-details-id" type="text"  name="id" required
                               readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="brandLabel" for="product-details-brand">
                      Brand
                    </Label>
                    <AvField className="input--style-5" id="product-details-brand" type="text" name="brand"/>
                  </AvGroup>
                  <AvGroup>
                    <Label id="colorLabel" for="product-details-color">
                      Color
                    </Label>
                    <AvField className="input--style-5" id="product-details-color" type="text" name="color"/>
                  </AvGroup>
                  <AvGroup>
                    <Label id="genderLabel" for="product-details-gender">
                      Gender
                    </Label>
                    <AvField className="input--style-5" id="product-details-gender" type="text" name="gender"/>
                  </AvGroup>
                  <AvGroup>
                    <Label id="styleLabel" for="product-details-style">
                      Style
                    </Label>
                    <AvField className="input--style-5" id="product-details-style" type="text" name="style"/>
                  </AvGroup>
                  <AvGroup>
                    <Label id="size_mesaurmentsLabel" for="product-details-size_mesaurments">
                      Size Mesaurments
                    </Label>
                    <AvField  className="input--style-5" id="product-details-size_mesaurments" type="text" name="size_mesaurments"/>
                  </AvGroup>
                  <AvGroup>
                    <Label id="size_detailsLabel" for="product-details-size_details">
                      Size Details
                    </Label>
                    <AvField className="input--style-5" id="product-details-size_details" type="text" name="size_details"/>
                  </AvGroup>
                  <Button tag={Link} id="cancel-save" to="/product-details" replace color="info">
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
  products: storeState.product.entities,
  productDetailsEntity: storeState.productDetails.entity,
  loading: storeState.productDetails.loading,
  updating: storeState.productDetails.updating,
  updateSuccess: storeState.productDetails.updateSuccess,
});

const mapDispatchToProps = {
  getProducts,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetailsUpdate);
