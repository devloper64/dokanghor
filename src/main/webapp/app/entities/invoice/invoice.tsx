import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFileInvoice } from "@fortawesome/free-solid-svg-icons"
import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IInvoiceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Invoice = (props: IInvoiceProps) => {
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

  const { invoiceList, match, loading } = props;
  return (
    <div>
      <h2 id="invoice-heading">
        Invoices
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Invoice
        </Link>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < props.links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {invoiceList && invoiceList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('invoice_number')}>
                    Invoice Number <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('to')}>
                    To <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('item_list')}>
                    Item List <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('subtotal')}>
                    Subtotal <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('discount')}>
                    Discount <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('vat')}>
                    Vat <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('total')}>
                    Total <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('invoice_date')}>
                    Invoice Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Transaction <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {invoiceList.map((invoice, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${invoice.id}`} color="link" size="sm">
                        {invoice.id}
                      </Button>
                    </td>
                    <td>{invoice.invoice_number}</td>
                    <td>{invoice.to}</td>
                    <td>{invoice.item_list}</td>
                    <td>{invoice.subtotal}</td>
                    <td>{invoice.discount}</td>
                    <td>{invoice.vat}</td>
                    <td>{invoice.total}</td>
                    <td>
                      {invoice.invoice_date ? <TextFormat type="date" value={invoice.invoice_date} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>{invoice.transactionId ? <Link to={`transaction/${invoice.transactionId}`}>{invoice.transactionId}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">

                        <Button tag={Link} to={`${match.url}/${invoice.id}/invoice-view`} color="info" size="sm">
                          <FontAwesomeIcon icon={faFileInvoice} /> <span className="d-none d-md-inline">Invoice</span>
                        </Button>

                        <Button tag={Link} to={`${match.url}/${invoice.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${invoice.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${invoice.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Invoices found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

const mapStateToProps = ({ invoice }: IRootState) => ({
  invoiceList: invoice.entities,
  loading: invoice.loading,
  totalItems: invoice.totalItems,
  links: invoice.links,
  entity: invoice.entity,
  updateSuccess: invoice.updateSuccess,
});

const mapDispatchToProps = {
  getEntities,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Invoice);
