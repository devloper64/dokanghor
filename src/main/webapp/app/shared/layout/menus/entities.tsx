import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="&nbsp;Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/address">
      Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/category">
      Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/sub-category">
      Sub Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      Product
    </MenuItem>
    <MenuItem icon="asterisk" to="/shipping-address">
      Shipping Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment">
      Payment
    </MenuItem>
    <MenuItem icon="asterisk" to="/transaction">
      Transaction
    </MenuItem>
    <MenuItem icon="asterisk" to="/mobile-intro">
      Mobile Intro
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-images">
      Product Images
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-type">
      Product Type
    </MenuItem>
    <MenuItem icon="asterisk" to="/product-details">
      Product Details
    </MenuItem>
    <MenuItem icon="asterisk" to="/order-status">
      Order Status
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
