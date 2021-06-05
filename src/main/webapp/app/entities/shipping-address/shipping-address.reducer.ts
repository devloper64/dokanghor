import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShippingAddress, defaultValue } from 'app/shared/model/shipping-address.model';

export const ACTION_TYPES = {
  FETCH_SHIPPINGADDRESS_LIST: 'shippingAddress/FETCH_SHIPPINGADDRESS_LIST',
  FETCH_SHIPPINGADDRESS: 'shippingAddress/FETCH_SHIPPINGADDRESS',
  CREATE_SHIPPINGADDRESS: 'shippingAddress/CREATE_SHIPPINGADDRESS',
  UPDATE_SHIPPINGADDRESS: 'shippingAddress/UPDATE_SHIPPINGADDRESS',
  DELETE_SHIPPINGADDRESS: 'shippingAddress/DELETE_SHIPPINGADDRESS',
  RESET: 'shippingAddress/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShippingAddress>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ShippingAddressState = Readonly<typeof initialState>;

// Reducer

export default (state: ShippingAddressState = initialState, action): ShippingAddressState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SHIPPINGADDRESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPPINGADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPPINGADDRESS):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPPINGADDRESS):
    case REQUEST(ACTION_TYPES.DELETE_SHIPPINGADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SHIPPINGADDRESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPPINGADDRESS):
    case FAILURE(ACTION_TYPES.CREATE_SHIPPINGADDRESS):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPPINGADDRESS):
    case FAILURE(ACTION_TYPES.DELETE_SHIPPINGADDRESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPPINGADDRESS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_SHIPPINGADDRESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPPINGADDRESS):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPPINGADDRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPPINGADDRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shipping-addresses';

// Actions

export const getEntities: ICrudGetAllAction<IShippingAddress> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPPINGADDRESS_LIST,
    payload: axios.get<IShippingAddress>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IShippingAddress> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPPINGADDRESS,
    payload: axios.get<IShippingAddress>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IShippingAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPPINGADDRESS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IShippingAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPPINGADDRESS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShippingAddress> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPPINGADDRESS,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
