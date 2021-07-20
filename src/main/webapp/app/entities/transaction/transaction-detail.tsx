import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {ICrudGetAction} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './transaction.reducer';
import {ITransaction} from 'app/shared/model/transaction.model';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface ITransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const TransactionDetail = (props: ITransactionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {transactionEntity} = props;
  return (
    <div className="entity-form">
      <div className="page-wrapper  p-t-45 p-b-50">
        <div className="wrapper wrapper--w790">
          <div className="card-5">
            <div className="card-heading">
              <h2 className="title">Transaction View</h2>
            </div>

            <div className="card-body">
              <Row>
                <Col md="8">
                  <div className="name">Id</div>
                  <b >{transactionEntity.id}</b>
                  <dl className="jh-entity-details">
                    <dt className="m-t">
                      <span id="transactionid">Transactionid</span>
                    </dt>
                    <dd>{transactionEntity.transactionid}</dd>
                    <dt className="m-t">
                      <span id="transaction_method">Transaction Method</span>
                    </dt>
                    <dd>{transactionEntity.transaction_method}</dd>
                    <dt className="m-t">Payment</dt>
                    <dd>{transactionEntity.paymentId ? transactionEntity.paymentId : ''}</dd>
                  </dl>
                  <div className="m-t">
                  <Button tag={Link} to="/transaction" replace color="info">
                    <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                  <Button tag={Link} to={`/transaction/${transactionEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({transaction}: IRootState) => ({
  transactionEntity: transaction.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TransactionDetail);
