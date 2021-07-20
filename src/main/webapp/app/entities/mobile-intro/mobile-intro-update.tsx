import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';

import {getEntity, updateEntity, createEntity, reset} from './mobile-intro.reducer';
import {IMobileIntro} from 'app/shared/model/mobile-intro.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import axios from "axios";

export interface IMobileIntroUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const MobileIntroUpdate = (props: IMobileIntroUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {mobileIntroEntity, loading, updating} = props;

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
        directory: 'intro'
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
    props.history.push('/mobile-intro');
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
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...mobileIntroEntity,
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
              <h2 className="title">Upload Intro Image</h2>
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
              <h2 className="title">Create Or Update Intro</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : mobileIntroEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="mobile-intro-id">ID</Label>
                      <AvInput id="mobile-intro-id" type="text"  className="input--style-5" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="textLabel" for="mobile-intro-text">
                      Text
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="mobile-intro-text"
                      type="text"
                      name="text"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="imageLabel" for="mobile-intro-image">
                      Image
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="mobile-intro-image"
                      type="text"
                      name="image"
                      value={fileName}
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                      readOnly
                    />
                  </AvGroup>
                  <Button tag={Link} id="cancel-save" to="/mobile-intro" replace color="info">
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
  mobileIntroEntity: storeState.mobileIntro.entity,
  loading: storeState.mobileIntro.loading,
  updating: storeState.mobileIntro.updating,
  updateSuccess: storeState.mobileIntro.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MobileIntroUpdate);
