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

import { IOrderStatus, defaultValue } from 'app/shared/model/order-status.model';

export const ACTION_TYPES = {
  FETCH_ORDERSTATUS_LIST: 'orderStatus/FETCH_ORDERSTATUS_LIST',
  FETCH_ORDERSTATUS: 'orderStatus/FETCH_ORDERSTATUS',
  CREATE_ORDERSTATUS: 'orderStatus/CREATE_ORDERSTATUS',
  UPDATE_ORDERSTATUS: 'orderStatus/UPDATE_ORDERSTATUS',
  DELETE_ORDERSTATUS: 'orderStatus/DELETE_ORDERSTATUS',
  RESET: 'orderStatus/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrderStatus>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type OrderStatusState = Readonly<typeof initialState>;

// Reducer

export default (state: OrderStatusState = initialState, action): OrderStatusState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORDERSTATUS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORDERSTATUS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORDERSTATUS):
    case REQUEST(ACTION_TYPES.UPDATE_ORDERSTATUS):
    case REQUEST(ACTION_TYPES.DELETE_ORDERSTATUS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ORDERSTATUS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORDERSTATUS):
    case FAILURE(ACTION_TYPES.CREATE_ORDERSTATUS):
    case FAILURE(ACTION_TYPES.UPDATE_ORDERSTATUS):
    case FAILURE(ACTION_TYPES.DELETE_ORDERSTATUS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORDERSTATUS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ORDERSTATUS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORDERSTATUS):
    case SUCCESS(ACTION_TYPES.UPDATE_ORDERSTATUS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORDERSTATUS):
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

const apiUrl = 'api/order-statuses';

// Actions

export const getEntities: ICrudGetAllAction<IOrderStatus> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERSTATUS_LIST,
    payload: axios.get<IOrderStatus>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IOrderStatus> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORDERSTATUS,
    payload: axios.get<IOrderStatus>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrderStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORDERSTATUS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IOrderStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORDERSTATUS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrderStatus> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORDERSTATUS,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
