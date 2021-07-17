import React, {useState, useEffect} from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row, Table} from 'reactstrap';
import {ICrudGetAllAction, getSortState, IPaginationBaseState} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import '../table.scss'
import {IRootState} from 'app/shared/reducers';
import {getEntities, reset} from './category.reducer';
import {ICategory} from 'app/shared/model/category.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';
import {ITEMS_PER_PAGE} from 'app/shared/util/pagination.constants';
import {overridePaginationStateWithQueryParams} from 'app/shared/util/entity-utils';

export interface ICategoryProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
}

export const Category = (props: ICategoryProps) => {
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

  const {categoryList, match, loading} = props;
  return (
    <div className="entity-table">
      <div className="container-table100">
        <div className="wrap-table100">
          <h2 id="category-heading">
            Categories
            <Link to={`${match.url}/new`} className="heading-padding btn btn-primary_head float-right jh-create-entity"
                  id="jh-create-entity">
              <FontAwesomeIcon icon="plus"/>
              &nbsp; Create new Category
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
              {categoryList && categoryList.length > 0 ? (
                <div className="table">
                  <div className="row header">

                    <div className="cell" onClick={sort('id')}>
                      ID <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('name')}>
                      Name <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell" onClick={sort('image')}>
                      Image <FontAwesomeIcon icon="sort"/>
                    </div>
                    <div className="cell">
                      Action
                    </div>
                  </div>

                  {categoryList.map((category, i) => (
                    <div  className="row" key={`entity-${i}`}>

                      <div className="cell">
                        <Button tag={Link} to={`${match.url}/${category.id}`} color="link" size="sm">
                          {category.id}
                        </Button>
                      </div>
                      <div className="cell">{category.name}</div>
                      <div className="cell">
                        <img className="img" src={category.image} alt="dokanghor"/>
                      </div>
                      <div className="cell">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${category.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye"/> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${category.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt"/> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${category.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash"/> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>

                      </div>
                    </div>

                  ))}
                </div>
              ) : (
                !loading && <div className="alert-margin alert alert-warning">No Categories found</div>
              )}
            </InfiniteScroll>
          </div>
        </div>
      </div>

  );
};

const mapStateToProps = ({category}: IRootState) => ({
  categoryList: category.entities,
  loading: category.loading,
  totalItems: category.totalItems,
  links: category.links,
  entity: category.entity,
  updateSuccess: category.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Category);
