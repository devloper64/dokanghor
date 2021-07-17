import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';
import {IHeaderProps} from "app/shared/layout/header/header";
import 'font-awesome/css/font-awesome.min.css';

const Footer =  (props: IHeaderProps) => (
  <div className="footer page-content">
    {props.isAuthenticated && props.isAdmin && (
      <footer className="footer-20192">
        <div className="site-section">
          <div className="container">

            <div className="cta d-block d-md-flex align-items-center px-5">
              <div>
                <h2 className="mb-0">DokanGhor</h2>
                <h3 className="text-dark">Let,s start shopping</h3>
              </div>
            </div>
            <div className="row">

              <div className="col-sm">
                <a href="" className="footer-logo">DokanGhor</a>
                <p className="copyright">
                  <small>&copy; 2021</small>
                </p>
              </div>
              <div className="col-sm">
                <h3>Customers</h3>
                <ul className="list-unstyled links">
                  <li><a href="">Buyer</a></li>
                  <li><a href="">Supplier</a></li>
                </ul>
              </div>
              <div className="col-sm">
                <h3>Company</h3>
                <ul className="list-unstyled links">
                  <li><a href="">About us</a></li>
                  <li><a href="">Careers</a></li>
                  <li><a href="">Contact us</a></li>
                </ul>
              </div>
              <div className="col-sm">
                <h3>Further Information</h3>
                <ul className="list-unstyled links">
                  <li><a href="">Terms &amp; Conditions</a></li>
                  <li><a href="">Privacy Policy</a></li>
                </ul>
              </div>
              <div className="col-md-3">
                <h3>Follow us</h3>
                <ul className="list-unstyled social">
                  <li><a href=""><span className="fab fa-facebook-f"/></a></li>
                </ul>
              </div>

            </div>
          </div>
        </div>
      </footer>


    )}
  </div>
);

export default Footer;
