import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {ICrudGetAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import '../form.scss'

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './mobile-intro.reducer';
import {IMobileIntro} from 'app/shared/model/mobile-intro.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface IMobileIntroDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const MobileIntroDetail = (props: IMobileIntroDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {mobileIntroEntity} = props;
  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Intro View</h2>
            </div>

            <div className="card-body">
              <Row>
                <Col md="8">
                  <div className="name">Id</div>
                  <b>{mobileIntroEntity.id}</b>
                  <dl className="jh-entity-details">
                    <dt className="m-t">
                      <span id="text">Text</span>
                    </dt>
                    <dd>{mobileIntroEntity.text}</dd>
                    <dt className="m-t">
                      <span id="name">Image Link</span>
                    </dt>
                    <dd>{mobileIntroEntity.image}</dd>
                    <dt className="m-t">
                      <span id="name">Image</span>
                    </dt>
                    <dd><img className="img" src={mobileIntroEntity.image} alt="dokanghor"/></dd>
                  </dl>
                  <div className="m-t">
                    <Button tag={Link} to="/mobile-intro" replace color="info">
                      <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                    </Button>
                    &nbsp;
                    <Button tag={Link} to={`/mobile-intro/${mobileIntroEntity.id}/edit`} replace color="primary">
                      <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                    </Button>
                  </div>
                </Col>
              </Row>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({mobileIntro}: IRootState) => ({
  mobileIntroEntity: mobileIntro.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MobileIntroDetail);
