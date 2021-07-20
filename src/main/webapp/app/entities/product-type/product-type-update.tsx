import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import '../form.scss'

import {getEntity, updateEntity, createEntity, reset} from './product-type.reducer';
import {IProductType} from 'app/shared/model/product-type.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import {getEntities as getSubCategories} from "app/entities/sub-category/sub-category.reducer";

export interface IProductTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ProductTypeUpdate = (props: IProductTypeUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {productTypeEntity, subCategories, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/product-type');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
    props.getSubCategories();
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productTypeEntity,
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
              <h2 className="title">Create Or Update Product Type</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : productTypeEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="product-type-id">ID</Label>
                      <AvInput id="product-type-id" type="text" className="input--style-5" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="nameLabel" for="product-type-name">
                      Name
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="product-type-name"
                      type="text"
                      name="name"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label for="product-type-subCategory">Sub Category</Label>
                    <AvInput id="product-type-subCategory" type="select" className="form-control" name="subCategoryId"
                             required>
                      {subCategories
                        ? subCategories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>

                  <Button tag={Link} id="cancel-save" to="/product-type" replace color="info">
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
  subCategories: storeState.subCategory.entities,
  productTypeEntity: storeState.productType.entity,
  loading: storeState.productType.loading,
  updating: storeState.productType.updating,
  updateSuccess: storeState.productType.updateSuccess,
});

const mapDispatchToProps = {
  getSubCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductTypeUpdate);
