import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {ICrudGetAction, ICrudGetAllAction, ICrudPutAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import '../form.scss'

import {IPayment} from 'app/shared/model/payment.model';
import {getEntities as getPayments} from 'app/entities/payment/payment.reducer';
import {getEntity, updateEntity, createEntity, reset} from './transaction.reducer';
import {getEntities as getMethods} from 'app/entities/transaction-method/transaction-method.reducer';


export interface ITransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const TransactionUpdate = (props: ITransactionUpdateProps) => {
  const [paymentId, setPaymentId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const {transactionEntity, payments,methods, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/transaction');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getPayments();
    props.getMethods();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...transactionEntity,
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
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Create Or Update Transaction</h2>
            </div>

            <div className="card-body">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : transactionEntity} onSubmit={saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="transaction-id">ID</Label>
                      <AvInput  id="transaction-id" type="text" className="input--style-5" name="id" required readOnly/>
                    </AvGroup>
                  ) : null}
                  <AvGroup>
                    <Label id="transactionidLabel" for="transaction-transactionid">
                      Transactionid
                    </Label>
                    <AvField
                      className="input--style-5"
                      id="transaction-transactionid"
                      type="text"
                      name="transactionid"
                      validate={{
                        required: {value: true, errorMessage: 'This field is required.'},
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label for="transaction-payment">Payment</Label>
                    <AvInput id="transaction-payment" type="select" className="form-control" name="paymentId" required>
                      {payments
                        ? payments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>


                  <AvGroup>
                    <Label for="transaction-method">Transaction Method</Label>
                    <AvInput id="transaction-method" type="select" className="form-control" name="transactionMethodId" required>
                      {methods
                        ? methods.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                        : null}
                    </AvInput>
                    <AvFeedback>This field is required.</AvFeedback>
                  </AvGroup>

                  <Button tag={Link} id="cancel-save" to="/transaction" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/>
                    &nbsp;
                    <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button className="btn btn--radius-2 btn--green" id="save-entity" type="submit" disabled={updating}>
                    <FontAwesomeIcon icon="save"/>
                    &nbsp; Save
                  </Button>
                </AvForm>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>

  );
};

const mapStateToProps = (storeState: IRootState) => ({
  payments: storeState.payment.entities,
  methods: storeState.transactionMethod.entities,
  transactionEntity: storeState.transaction.entity,
  loading: storeState.transaction.loading,
  updating: storeState.transaction.updating,
  updateSuccess: storeState.transaction.updateSuccess,
});

const mapDispatchToProps = {
  getPayments,
  getMethods,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TransactionUpdate);
