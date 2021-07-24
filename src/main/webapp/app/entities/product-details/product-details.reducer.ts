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

import { IProductDetails, defaultValue } from 'app/shared/model/product-details.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTDETAILS_LIST: 'productDetails/FETCH_PRODUCTDETAILS_LIST',
  FETCH_PRODUCTDETAILS: 'productDetails/FETCH_PRODUCTDETAILS',
  CREATE_PRODUCTDETAILS: 'productDetails/CREATE_PRODUCTDETAILS',
  UPDATE_PRODUCTDETAILS: 'productDetails/UPDATE_PRODUCTDETAILS',
  DELETE_PRODUCTDETAILS: 'productDetails/DELETE_PRODUCTDETAILS',
  RESET: 'productDetails/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductDetails>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProductDetailsState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductDetailsState = initialState, action): ProductDetailsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTDETAILS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTDETAILS):
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

const apiUrl = 'api/product-details';
const apiUrlGet = 'api/one-product-details';

// Actions

export const getEntities: ICrudGetAllAction<IProductDetails> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTDETAILS_LIST,
    payload: axios.get<IProductDetails>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProductDetails> = id => {
  const requestUrl = `${apiUrlGet}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTDETAILS,
    payload: axios.get<IProductDetails>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IProductDetails> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductDetails> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTDETAILS,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
