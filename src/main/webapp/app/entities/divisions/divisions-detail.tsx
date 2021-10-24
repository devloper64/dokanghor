import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './divisions.reducer';
import { IDivisions } from 'app/shared/model/divisions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDivisionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DivisionsDetail = (props: IDivisionsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { divisionsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Divisions [<b>{divisionsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{divisionsEntity.name}</dd>
          <dt>
            <span id="bn_name">Bn Name</span>
          </dt>
          <dd>{divisionsEntity.bn_name}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{divisionsEntity.url}</dd>
        </dl>
        <Button tag={Link} to="/divisions" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/divisions/${divisionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ divisions }: IRootState) => ({
  divisionsEntity: divisions.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DivisionsDetail);
