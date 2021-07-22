import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import '../form.scss'

import {IProduct} from 'app/shared/model/product.model';
import {getEntities as getProducts} from 'app/entities/product/product.reducer';
import {getEntity, updateEntity, createEntity, reset} from './product-images.reducer';
import {IProductImages} from 'app/shared/model/product-images.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import axios from "axios";

export interface IProductImagesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const ProductImagesUpdate = (props: IProductImagesUpdateProps) => {
  const [productId, setProductId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {productImagesEntity, products, loading, updating} = props;
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
    props.history.push('/product-images');
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
        ...productImagesEntity,
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
              <h2 className="title">Create Or Update Product Image</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : productImagesEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="product-images-id">ID</Label>
                      <AvInput id="product-images-id" type="text" className="input--style-5" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="imageLabel" for="product-images-image">
                      Image
                    </Label>
                    <AvField
                      id="product-images-image"
                      type="text"
                      value={fileName}
                      className="input--style-5"
                      name="image"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label for="product-images-product">Product</Label>
                    <AvInput id="product-images-product" type="select" className="form-control" name="productId">
                      {products
                        ? products.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                        : null}
                    </AvInput>
                  </AvGroup>
                  <Button tag={Link} id="cancel-save" to="/product-images" replace color="info">
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
  productImagesEntity: storeState.productImages.entity,
  loading: storeState.productImages.loading,
  updating: storeState.productImages.updating,
  updateSuccess: storeState.productImages.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ProductImagesUpdate);
