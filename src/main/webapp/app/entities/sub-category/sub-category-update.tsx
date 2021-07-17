import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import '../form.scss'
import {ICategory} from 'app/shared/model/category.model';
import {getEntities as getCategories} from 'app/entities/category/category.reducer';
import {getEntity, updateEntity, createEntity, reset} from './sub-category.reducer';
import {ISubCategory} from 'app/shared/model/sub-category.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import axios from "axios";

export interface ISubCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const SubCategoryUpdate = (props: ISubCategoryUpdateProps) => {
  const [categoryId, setCategoryId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);
  const {subCategoryEntity, categories, loading, updating} = props;
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
        directory: 'subcategory'
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
    props.history.push('/sub-category');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...subCategoryEntity,
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
              <h2 className="title">Upload Sub Category Image</h2>
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
              <h2 className="title">Create Or Update Sub Category</h2>
            </div>
            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : subCategoryEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="sub-category-id">ID</Label>
                      <AvInput className="input--style-5" id="sub-category-id" type="text" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="nameLabel" for="sub-category-name">
                      Name
                    </Label>
                    <AvField
                      id="sub-category-name"
                      type="text"
                      name="name"
                      className="input--style-5"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label id="imageLabel" for="sub-category-image">
                      Image
                    </Label>
                    <AvField
                      id="sub-category-image"
                      type="text"
                      name="image"
                      value={fileName}
                      className="input--style-5"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                      readOnly
                    />
                  </AvGroup>

                  <AvGroup>
                    <Label for="sub-category-category">Category</Label>
                    <AvInput id="sub-category-category" type="select" className="form-control" name="categoryId"
                             required>
                      {categories
                        ? categories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>
                  <Button tag={Link} id="cancel-save" to="/sub-category" replace color="info">
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
  categories: storeState.category.entities,
  subCategoryEntity: storeState.subCategory.entity,
  loading: storeState.subCategory.loading,
  updating: storeState.subCategory.updating,
  updateSuccess: storeState.subCategory.updateSuccess,
});

const mapDispatchToProps = {
  getCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubCategoryUpdate);
