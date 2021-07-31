import React, {useState, useEffect} from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row, Table} from 'reactstrap';
import {ICrudGetAllAction, getSortState, IPaginationBaseState} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import '../../entities/product/table-product.scss'

import {IRootState} from 'app/shared/reducers';
import {getEntities, reset} from './product-details.reducer';
import {IProductDetails} from 'app/shared/model/product-details.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';
import {ITEMS_PER_PAGE} from 'app/shared/util/pagination.constants';
import {overridePaginationStateWithQueryParams} from 'app/shared/util/entity-utils';

export interface IProductDetailsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export const ProductDetails = (props: IProductDetailsProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const resetAll = () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    props.getEntities();
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      resetAll();
    }
  }, [props.updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    props.reset();
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
    setSorting(true);
  };

  const {productDetailsList, match, loading} = props;
  return (
    <div className="entity-table-product">

      <div className="container-table100">
        <div className="wrap-table100">
          <h2 id="product-details-heading">
            Product Details
            <Link to={`${match.url}/new`} className="heading-padding btn btn-primary_head float-right jh-create-entity"
                  id="jh-create-entity">
              <FontAwesomeIcon icon="plus"/>
              &nbsp; Create new Product Details
            </Link>
          </h2>

            <InfiniteScroll
              pageStart={paginationState.activePage}
              loadMore={handleLoadMore}
              hasMore={paginationState.activePage - 1 < props.links.next}
              loader={<div className="loader">Loading ...</div>}
              threshold={0}
              initialLoad={false}
            >
              {productDetailsList && productDetailsList.length > 0 ? (
                <div className="table">
                  <div className="row header">
                    <div className="cell" onClick={sort('id')}>
                      ID <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('brand')}>
                      Brand <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('color')}>
                      Color <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('gender')}>
                      Gender <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('style')}>
                      Style <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('size_mesaurments')}>
                      Size Mesaurments <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('size_details')}>
                      Size Details <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell">
                      Action
                    </div>
                  </div>
                  <tbody>
                  {productDetailsList.map((productDetails, i) => (
                    <div className="row" key={`entity-${i}`}>
                      <div className="cell">
                        <Button tag={Link} to={`${match.url}/${productDetails.id}`} color="link" size="sm">
                          {productDetails.id}
                        </Button>
                      </div>
                      <div className="cell">{productDetails.brand}</div>
                      <div className="cell">{productDetails.color}</div>
                      <div className="cell">{productDetails.gender}</div>
                      <div className="cell">{productDetails.style}</div>
                      <div className="cell">{productDetails.size_mesaurments}</div>
                      <div className="cell">{productDetails.size_details}</div>
                      <div className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${productDetails.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye"/> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${productDetails.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${productDetails.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash"/> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </div>
                    </div>
                  ))}
                  </tbody>
                </div>
              ) : (
                !loading && <div className="alert alert-warning">No Product Details found</div>
              )}
            </InfiniteScroll>
          </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({productDetails}: IRootState) => ({
  productDetailsList: productDetails.entities,
  loading: productDetails.loading,
  totalItems: productDetails.totalItems,
  links: productDetails.links,
  entity: productDetails.entity,
  updateSuccess: productDetails.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductDetails);
