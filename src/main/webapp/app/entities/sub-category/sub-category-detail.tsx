import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './sub-category.reducer';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISubCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SubCategoryDetail = (props: ISubCategoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { subCategoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          SubCategory [<b>{subCategoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{subCategoryEntity.name}</dd>

          <dt>
            <span id="image">Image</span>
          </dt>
          <dd>{subCategoryEntity.image}</dd>

          <dt>Category</dt>
          <dd>{subCategoryEntity.categoryName ? subCategoryEntity.categoryName : ''}</dd>
        </dl>
        <Button tag={Link} to="/sub-category" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sub-category/${subCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ subCategory }: IRootState) => ({
  subCategoryEntity: subCategory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SubCategoryDetail);
