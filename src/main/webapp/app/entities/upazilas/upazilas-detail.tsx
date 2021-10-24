import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './upazilas.reducer';
import { IUpazilas } from 'app/shared/model/upazilas.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUpazilasDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UpazilasDetail = (props: IUpazilasDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { upazilasEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Upazilas [<b>{upazilasEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{upazilasEntity.name}</dd>
          <dt>
            <span id="bn_name">Bn Name</span>
          </dt>
          <dd>{upazilasEntity.bn_name}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{upazilasEntity.url}</dd>
          <dt>Districts</dt>
          <dd>{upazilasEntity.districtsId ? upazilasEntity.districtsId : ''}</dd>
        </dl>
        <Button tag={Link} to="/upazilas" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/upazilas/${upazilasEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ upazilas }: IRootState) => ({
  upazilasEntity: upazilas.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UpazilasDetail);
