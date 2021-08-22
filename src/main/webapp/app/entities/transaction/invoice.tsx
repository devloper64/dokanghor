import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import './invoice.scss'
import {IRootState} from 'app/shared/reducers';
import {getEntity} from './transaction.reducer';
export interface IInvoiceProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const Invoice = (props: IInvoiceProps) => {

  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const print = () => {
    window.print()
  }

  const {transactionEntity} = props;

  return (
    <div className="invoice">
    <div className="container-fluid">
      <div id="ui-view" data-select2-id="ui-view">
        <div>
          <div className="card">
            <div className="card-header">Invoice
              <strong>#BBB-10010110101938</strong>
              <a className="btn btn-sm btn-secondary float-right mr-1 d-print-none"  onClick={print} data-abc="true">
                <i className="fa fa-print"></i> Print</a>
            </div>
            <div className="card-body">
              <div className="row mb-4">
                <div className="col-sm-4">
                  <h6 className="mb-3">From:</h6>
                  <div>
                    <strong>BBBootstrap.com</strong>
                  </div>
                  <div>42, Awesome Enclave</div>
                  <div>New York City, New york, 10394</div>
                  <div>Email: admin@bbbootstrap.com</div>
                  <div>Phone: +48 123 456 789</div>
                </div>
                <div className="col-sm-4">
                  <h6 className="mb-3">To:</h6>
                  <div>
                    <strong>BBBootstrap.com</strong>
                  </div>
                  <div>42, Awesome Enclave</div>
                  <div>New York City, New york, 10394</div>
                  <div>Email: admin@bbbootstrap.com</div>
                  <div>Phone: +48 123 456 789</div>
                </div>
                <div className="col-sm-4">
                  <h6 className="mb-3">Details:</h6>
                  <div>Invoice
                    <strong>#BBB-10010110101938</strong>
                  </div>
                  <div>April 30, 2019</div>
                  <div>VAT: NYC09090390</div>
                  <div>Account Name: BBBootstrap Inc</div>
                  <div>
                    <strong>SWIFT code: 99 8888 7777 6666 5555</strong>
                  </div>
                </div>
              </div>
              <div className="table-responsive-sm">
                <table className="table table-striped">
                  <thead>
                  <tr>
                    <th className="center">#</th>
                    <th>Item</th>
                    <th>Description</th>
                    <th className="center">Quantity</th>
                    <th className="right">Unit Cost</th>
                    <th className="right">Total</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td className="center">1</td>
                    <td className="left">Iphone 10</td>
                    <td className="left">Apple iphoe 10 with extended warranty</td>
                    <td className="center">16</td>
                    <td className="right">$999,00</td>
                    <td className="right">$999,00</td>
                  </tr>
                  <tr>
                    <td className="center">2</td>
                    <td className="left">Samsung S6</td>
                    <td className="left">Samsung S6 with extended warranty</td>
                    <td className="center">20</td>
                    <td className="right">$150,00</td>
                    <td className="right">$3.000,00</td>
                  </tr>
                  </tbody>
                </table>
              </div>
              <div className="row">
                <div className="col-lg-4 col-sm-5">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                  eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.
                </div>
                <div className="col-lg-4 col-sm-5 ml-auto">
                  <table className="table table-clear">
                    <tbody>
                    <tr>
                      <td className="left">
                        <strong>Subtotal</strong>
                      </td>
                      <td className="right">$8.497,00</td>
                    </tr>
                    <tr>
                      <td className="left">
                        <strong>Discount (20%)</strong>
                      </td>
                      <td className="right">$1,699,40</td>
                    </tr>
                    <tr>
                      <td className="left">
                        <strong>VAT (10%)</strong>
                      </td>
                      <td className="right">$679,76</td>
                    </tr>
                    <tr>
                      <td className="left">
                        <strong>Total</strong>
                      </td>
                      <td className="right">
                        <strong>$7.477,36</strong>
                      </td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
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

export default connect(mapStateToProps, mapDispatchToProps)(Invoice);
