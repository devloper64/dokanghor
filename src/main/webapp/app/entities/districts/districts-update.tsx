import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDivisions } from 'app/shared/model/divisions.model';
import { getEntities as getDivisions } from 'app/entities/divisions/divisions.reducer';
import { getEntity, updateEntity, createEntity, reset } from './districts.reducer';
import { IDistricts } from 'app/shared/model/districts.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDistrictsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DistrictsUpdate = (props: IDistrictsUpdateProps) => {
  const [divisionsId, setDivisionsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { districtsEntity, divisions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/districts');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getDivisions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...districtsEntity,
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
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecommerceApp.districts.home.createOrEditLabel">Create or edit a Districts</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : districtsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="districts-id">ID</Label>
                  <AvInput id="districts-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="districts-name">
                  Name
                </Label>
                <AvField
                  id="districts-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="bn_nameLabel" for="districts-bn_name">
                  Bn Name
                </Label>
                <AvField
                  id="districts-bn_name"
                  type="text"
                  name="bn_name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="latLabel" for="districts-lat">
                  Lat
                </Label>
                <AvField
                  id="districts-lat"
                  type="text"
                  name="lat"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lonLabel" for="districts-lon">
                  Lon
                </Label>
                <AvField
                  id="districts-lon"
                  type="text"
                  name="lon"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="urlLabel" for="districts-url">
                  Url
                </Label>
                <AvField
                  id="districts-url"
                  type="text"
                  name="url"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="districts-divisions">Divisions</Label>
                <AvInput id="districts-divisions" type="select" className="form-control" name="divisionsId" required>
                  {divisions
                    ? divisions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/districts" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  divisions: storeState.divisions.entities,
  districtsEntity: storeState.districts.entity,
  loading: storeState.districts.loading,
  updating: storeState.districts.updating,
  updateSuccess: storeState.districts.updateSuccess,
});

const mapDispatchToProps = {
  getDivisions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DistrictsUpdate);
