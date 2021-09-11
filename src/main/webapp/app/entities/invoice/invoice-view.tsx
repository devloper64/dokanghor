import React, {useEffect, useState} from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './invoice.reducer';
import { IInvoice } from 'app/shared/model/invoice.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInvoiceViewProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const InvoiceView = (props: IInvoiceViewProps) => {

  const [invoiceTo, setInvoiceTo] = useState({name: "",address:"",email:"",phone:""});

  const [itemsList, setItemsList] = useState([]);

  const { invoiceEntity } = props;

  const [fetchStatus, setFetchStatus] = useState(false);


  useEffect(() => {
    const fetchInvoice = async () => {
      await Promise.all([props.getEntity(props.match.params.id)]);
    }
    fetchInvoice().then(()=>{
      setFetchStatus(true)
    })
  }, []);

  useEffect(() => {
    if (fetchStatus){
      const jsonObjTo =JSON.parse(invoiceEntity.to);
      setInvoiceTo(jsonObjTo)

      const jsonObjList =JSON.parse(invoiceEntity.item_list);
      jsonObjList.map((v,i)=>{
          itemsList.push(v)
      })
    }
  }, [fetchStatus]);



  const print = () => {
    window.print()
  }

  return (
    <div className="invoice">
      <div className="container-fluid">
        <div id="ui-view" data-select2-id="ui-view">
          <div>
            <div className="card">
              <div className="card-header">Invoice
                <strong>#{invoiceEntity.invoice_number}</strong>
                <a className="btn btn-sm btn-secondary float-right mr-1 d-print-none"  onClick={print} data-abc="true">
                  <i className="fa fa-print"></i> Print</a>
              </div>
              <div className="card-body">
                <div className="row mb-4">
                  <div className="col-sm-4">
                    <h6 className="mb-3">From:</h6>
                    <div>
                      <strong>Dokanghor</strong>
                    </div>
                    <div>2nd floor Estern Plaza</div>
                    <div>Mirpur 10,Dhaka,Bangladesh</div>
                    <div>Email: dokanghor@gmail.com</div>
                    <div>Phone: +88 01795-888218</div>
                  </div>
                  <div className="col-sm-4">
                    <h6 className="mb-3">To:</h6>
                    <div>
                      <strong>{name}</strong>
                    </div>
                    <div>{invoiceTo.name}</div>
                    <div>{invoiceTo.address}</div>
                    <div>Email: {invoiceTo.email}</div>
                    <div>Phone: {invoiceTo.phone}</div>
                  </div>
                  <div className="col-sm-4">
                    <h6 className="mb-3">Details:</h6>
                    <div>Invoice:
                      <strong>#{invoiceEntity.invoice_number}</strong>
                    </div>
                    <div>Date:{new Date().toLocaleString() + ""}</div>
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
                    {itemsList.map((x, i) => (
                      <tr key={i}>
                        <td className="center">{i+1}</td>
                        <td className="left">{x.item}</td>
                        <td className="left">{x.description}</td>
                        <td className="center">{x.quantity}</td>
                        <td className="right">${x.unitCost}</td>
                        <td className="right">${x.total}</td>
                      </tr>

                    ))}
                    </tbody>
                  </table>
                </div>


                <div className="row">
                  <div className="col-lg-4 col-sm-5 ">
                    <table className="table table-clear">
                      <tbody>
                      <tr>
                        <td className="left">
                          <strong>Subtotal</strong>
                        </td>
                        <td className="right">${invoiceEntity.subtotal}</td>
                      </tr>
                      <tr>
                        <td className="left">
                          <strong>Discount</strong>
                        </td>
                        <td className="right">${invoiceEntity.discount}</td>
                      </tr>
                      <tr>
                        <td className="left">
                          <strong>VAT </strong>
                        </td>
                        <td className="right">${invoiceEntity.vat}</td>
                      </tr>
                      <tr>
                        <td className="left">
                          <strong>Total</strong>
                        </td>
                        <td className="right">
                          <strong>${invoiceEntity.total}</strong>
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

const mapStateToProps = ({ invoice }: IRootState) => ({
  invoiceEntity: invoice.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(InvoiceView);
