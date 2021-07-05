import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './mobile-intro.reducer';
import { IMobileIntro } from 'app/shared/model/mobile-intro.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMobileIntroDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MobileIntroDetail = (props: IMobileIntroDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { mobileIntroEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          MobileIntro [<b>{mobileIntroEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="text">Text</span>
          </dt>
          <dd>{mobileIntroEntity.text}</dd>
          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>{mobileIntroEntity.image}</dd>
        </dl>
        <Button tag={Link} to="/mobile-intro" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mobile-intro/${mobileIntroEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ mobileIntro }: IRootState) => ({
  mobileIntroEntity: mobileIntro.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MobileIntroDetail);
