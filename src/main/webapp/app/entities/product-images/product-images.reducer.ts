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

import { IProductImages, defaultValue } from 'app/shared/model/product-images.model';

export const ACTION_TYPES = {
  FETCH_PRODUCTIMAGES_LIST: 'productImages/FETCH_PRODUCTIMAGES_LIST',
  FETCH_PRODUCTIMAGES: 'productImages/FETCH_PRODUCTIMAGES',
  CREATE_PRODUCTIMAGES: 'productImages/CREATE_PRODUCTIMAGES',
  UPDATE_PRODUCTIMAGES: 'productImages/UPDATE_PRODUCTIMAGES',
  DELETE_PRODUCTIMAGES: 'productImages/DELETE_PRODUCTIMAGES',
  RESET: 'productImages/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProductImages>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ProductImagesState = Readonly<typeof initialState>;

// Reducer

export default (state: ProductImagesState = initialState, action): ProductImagesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIMAGES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRODUCTIMAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PRODUCTIMAGES):
    case REQUEST(ACTION_TYPES.UPDATE_PRODUCTIMAGES):
    case REQUEST(ACTION_TYPES.DELETE_PRODUCTIMAGES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIMAGES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRODUCTIMAGES):
    case FAILURE(ACTION_TYPES.CREATE_PRODUCTIMAGES):
    case FAILURE(ACTION_TYPES.UPDATE_PRODUCTIMAGES):
    case FAILURE(ACTION_TYPES.DELETE_PRODUCTIMAGES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIMAGES_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_PRODUCTIMAGES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRODUCTIMAGES):
    case SUCCESS(ACTION_TYPES.UPDATE_PRODUCTIMAGES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRODUCTIMAGES):
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

const apiUrl = 'api/product-images';

// Actions

export const getEntities: ICrudGetAllAction<IProductImages> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIMAGES_LIST,
    payload: axios.get<IProductImages>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IProductImages> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRODUCTIMAGES,
    payload: axios.get<IProductImages>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IProductImages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRODUCTIMAGES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IProductImages> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRODUCTIMAGES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProductImages> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRODUCTIMAGES,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
