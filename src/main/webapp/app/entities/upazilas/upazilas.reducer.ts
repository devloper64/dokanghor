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

import { IUpazilas, defaultValue } from 'app/shared/model/upazilas.model';

export const ACTION_TYPES = {
  FETCH_UPAZILAS_LIST: 'upazilas/FETCH_UPAZILAS_LIST',
  FETCH_UPAZILAS: 'upazilas/FETCH_UPAZILAS',
  CREATE_UPAZILAS: 'upazilas/CREATE_UPAZILAS',
  UPDATE_UPAZILAS: 'upazilas/UPDATE_UPAZILAS',
  DELETE_UPAZILAS: 'upazilas/DELETE_UPAZILAS',
  RESET: 'upazilas/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUpazilas>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type UpazilasState = Readonly<typeof initialState>;

// Reducer

export default (state: UpazilasState = initialState, action): UpazilasState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_UPAZILAS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UPAZILAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_UPAZILAS):
    case REQUEST(ACTION_TYPES.UPDATE_UPAZILAS):
    case REQUEST(ACTION_TYPES.DELETE_UPAZILAS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_UPAZILAS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UPAZILAS):
    case FAILURE(ACTION_TYPES.CREATE_UPAZILAS):
    case FAILURE(ACTION_TYPES.UPDATE_UPAZILAS):
    case FAILURE(ACTION_TYPES.DELETE_UPAZILAS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_UPAZILAS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_UPAZILAS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_UPAZILAS):
    case SUCCESS(ACTION_TYPES.UPDATE_UPAZILAS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_UPAZILAS):
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

const apiUrl = 'api/upazilas';

// Actions

export const getEntities: ICrudGetAllAction<IUpazilas> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UPAZILAS_LIST,
    payload: axios.get<IUpazilas>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IUpazilas> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UPAZILAS,
    payload: axios.get<IUpazilas>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUpazilas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UPAZILAS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUpazilas> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UPAZILAS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUpazilas> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UPAZILAS,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
