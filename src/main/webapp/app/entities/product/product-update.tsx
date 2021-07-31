import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getProductDetails} from 'app/entities/product-details/product-details.reducer';
import {getEntities as getSubCategories} from 'app/entities/sub-category/sub-category.reducer';
import {getEntities as getProductTypes} from 'app/entities/product-type/product-type.reducer';
import {getEntity, updateEntity, createEntity, reset} from './product.reducer';
import axios from "axios";

export interface IProductUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ProductUpdate = (props: IProductUpdateProps) => {
  const [subCategoryId, setSubCategoryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {productEntity, subCategories, productTypes,productDetails, loading, updating} = props;
  const [selectedFile, setSelectedFile] = useState();
  const [isFilePicked, setIsFilePicked] = useState(false);
  const [fileName, setFileName] = useState("");
  const [messageFail, setMessageFail] = useState("");
  const [messageSuccess, setMessageSuccess] = useState("");
  const changeHandler = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleSubmission = () => {


    console.log(fileName)
    const formData = new FormData();
    formData.append('file', selectedFile);

    axios.post('api/upload', formData, {
      params: {
        directory: 'product'
      },
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then(r => {
        console.log(r)
        setMessageSuccess("Image uploaded")
        setMessageFail("")
        setFileName(r.data.url)
      }
    ).catch(e => {
      setMessageSuccess("")
      setMessageFail("Image uploaded failed")
      console.log(e)
    })
  };

  const handleClose = () => {
    props.history.push('/product');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getSubCategories();
    props.getProductTypes();
    props.getProductDetails()
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...productEntity,
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
          <div className="m-b card-5">
            <div className="card-heading">
              <h2 className="title">Upload Product Image</h2>
            </div>
            <div className="card-body">
              <input className="input--style-6" type="file" name="file" onChange={changeHandler}/>
              <div>
                <Button className="m-t btn btn--radius-2 btn--blue" onClick={handleSubmission}>Upload</Button>
              </div>
              <span className="message-success">{messageSuccess}</span>
              <span className="message-failed">{messageFail}</span>

            </div>
          </div>
        </div>

        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Create Or Update Product</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : productEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="product-id">ID</Label>
                      <AvInput id="product-id" type="text" className="input--style-5" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  {!isNew ? (
                    <AvGroup>
                      <Label for="product-details-id">Product Details ID</Label>
                      <AvInput id="product-details-id" type="text" className="input--style-5" name="productDetails.id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="nameLabel" for="product-name">
                      Name
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="product-name"
                      type="text"
                      name="name"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="priceLabel" for="product-price">
                      Price
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="product-price"
                      type="number"
                      name="price"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label id="imageLabel" for="product-image">
                      Image
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="product-image"
                      type="text"
                      name="image"
                      value={fileName}
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label id="discount_amountLabel" for="discount_amount">
                      Discount Amount
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="discount_amount"
                      type="number"
                      name="discount_amount"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>


                  <AvGroup>
                    <Label id="quantityLabel" for="quantity">
                      Quantity
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="quantity"
                      type="number"
                      name="quantity"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label id="brandLabel" for="product-details-brand">
                      Brand
                    </Label>
                    <AvField  className="input--style-5" id="product-details-brand" type="text" name="productDetails.brand" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="colorLabel" for="product-details-color">
                      Color
                    </Label>
                    <AvField className="input--style-5" id="product-details-color" type="text" name="productDetails.color" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="genderLabel" for="product-details-gender">
                      Gender
                    </Label>
                    <AvField className="input--style-5" id="product-details-gender" type="text" name="productDetails.gender" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="styleLabel" for="product-details-style">
                      Style
                    </Label>
                    <AvField className="input--style-5" id="product-details-style" type="text" name="productDetails.style" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="size_mesaurmentsLabel" for="product-details-size_mesaurments">
                      Size Mesaurments
                    </Label>
                    <AvField className="input--style-5" id="product-details-size_mesaurments" type="text" name="productDetails.size_mesaurments" />
                  </AvGroup>
                  <AvGroup>
                    <Label id="size_detailsLabel" for="product-details-size_details">
                      Size Details
                    </Label>
                    <AvField className="input--style-5" id="product-details-size_details" type="text" name="productDetails.size_details" />
                  </AvGroup>



                  <AvGroup>
                    <Label for="product-subCategory">Sub Category</Label>
                    <AvInput id="product-subCategory" type="select" className="form-control" name="subCategoryId"
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

                  <AvGroup>
                    <Label for="product-productType">Product Type</Label>
                    <AvInput id="product-productType" type="select" className="form-control" name="productTypeId"
                             required>
                      {productTypes
                        ? productTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>

                  <Button tag={Link} id="cancel-save" to="/product" replace color="info">
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
  productTypes: storeState.productType.entities,
  subCategories: storeState.subCategory.entities,
  productDetails: storeState.productDetails.entities,
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating,
  updateSuccess: storeState.product.updateSuccess,
});

const mapDispatchToProps = {
  getProductTypes,
  getSubCategories,
  getProductDetails,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductUpdate);
