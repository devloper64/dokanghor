import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITransaction } from 'app/shared/model/transaction.model';
import { getEntities as getTransactions } from 'app/entities/transaction/transaction.reducer';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IInvoiceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceUpdate = (props: IInvoiceUpdateProps) => {
  const [transactionId, setTransactionId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { invoiceEntity, transactions, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/invoice');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getTransactions();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...invoiceEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="ecommerceApp.invoice.home.createOrEditLabel">Create or edit a Invoice</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : invoiceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="invoice-id">ID</Label>
                  <AvInput id="invoice-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="invoice_numberLabel" for="invoice-invoice_number">
                  Invoice Number
                </Label>
                <AvField
                  id="invoice-invoice_number"
                  type="text"
                  name="invoice_number"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="toLabel" for="invoice-to">
                  To
                </Label>
                <AvField
                  id="invoice-to"
                  type="text"
                  name="to"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="item_listLabel" for="invoice-item_list">
                  Item List
                </Label>
                <AvField
                  id="invoice-item_list"
                  type="text"
                  name="item_list"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="subtotalLabel" for="invoice-subtotal">
                  Subtotal
                </Label>
                <AvField
                  id="invoice-subtotal"
                  type="string"
                  className="form-control"
                  name="subtotal"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="discountLabel" for="invoice-discount">
                  Discount
                </Label>
                <AvField
                  id="invoice-discount"
                  type="string"
                  className="form-control"
                  name="discount"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="vatLabel" for="invoice-vat">
                  Vat
                </Label>
                <AvField id="invoice-vat" type="string" className="form-control" name="vat" />
              </AvGroup>
              <AvGroup>
                <Label id="totalLabel" for="invoice-total">
                  Total
                </Label>
                <AvField
                  id="invoice-total"
                  type="string"
                  className="form-control"
                  name="total"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="invoice-transaction">Transaction</Label>
                <AvInput id="invoice-transaction" type="select" className="form-control" name="transactionId">
                  <option value="" key="0" />
                  {transactions
                    ? transactions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/invoice" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  transactions: storeState.transaction.entities,
  invoiceEntity: storeState.invoice.entity,
  loading: storeState.invoice.loading,
  updating: storeState.invoice.updating,
  updateSuccess: storeState.invoice.updateSuccess,
});

const mapDispatchToProps = {
  getTransactions,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceUpdate);
