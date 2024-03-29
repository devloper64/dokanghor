import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './districts.reducer';
import { IDistricts } from 'app/shared/model/districts.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDistrictsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DistrictsDetail = (props: IDistrictsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { districtsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Districts [<b>{districtsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{districtsEntity.name}</dd>
          <dt>
            <span id="bn_name">Bn Name</span>
          </dt>
          <dd>{districtsEntity.bn_name}</dd>
          <dt>
            <span id="lat">Lat</span>
          </dt>
          <dd>{districtsEntity.lat}</dd>
          <dt>
            <span id="lon">Lon</span>
          </dt>
          <dd>{districtsEntity.lon}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{districtsEntity.url}</dd>
          <dt>Divisions</dt>
          <dd>{districtsEntity.divisionsId ? districtsEntity.divisionsId : ''}</dd>
        </dl>
        <Button tag={Link} to="/districts" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/districts/${districtsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ districts }: IRootState) => ({
  districtsEntity: districts.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DistrictsDetail);
