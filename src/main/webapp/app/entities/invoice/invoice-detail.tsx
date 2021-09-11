import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvoiceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceDetail = (props: IInvoiceDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { invoiceEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Invoice [<b>{invoiceEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="invoice_number">Invoice Number</span>
          </dt>
          <dd>{invoiceEntity.invoice_number}</dd>
          <dt>
            <span id="to">To</span>
          </dt>
          <dd>{invoiceEntity.to}</dd>
          <dt>
            <span id="item_list">Item List</span>
          </dt>
          <dd>{invoiceEntity.item_list}</dd>
          <dt>
            <span id="subtotal">Subtotal</span>
          </dt>
          <dd>{invoiceEntity.subtotal}</dd>
          <dt>
            <span id="discount">Discount</span>
          </dt>
          <dd>{invoiceEntity.discount}</dd>
          <dt>
            <span id="vat">Vat</span>
          </dt>
          <dd>{invoiceEntity.vat}</dd>
          <dt>
            <span id="total">Total</span>
          </dt>
          <dd>{invoiceEntity.total}</dd>
          <dt>
            <span id="invoice_date">Invoice Date</span>
          </dt>
          <dd>
            {invoiceEntity.invoice_date ? <TextFormat value={invoiceEntity.invoice_date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Transaction</dt>
          <dd>{invoiceEntity.transactionId ? invoiceEntity.transactionId : ''}</dd>
        </dl>
        <Button tag={Link} to="/invoice" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/invoice/${invoiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ invoice }: IRootState) => ({
  invoiceEntity: invoice.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceDetail);
