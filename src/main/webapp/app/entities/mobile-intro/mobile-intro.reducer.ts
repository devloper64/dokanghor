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

import { IMobileIntro, defaultValue } from 'app/shared/model/mobile-intro.model';

export const ACTION_TYPES = {
  FETCH_MOBILEINTRO_LIST: 'mobileIntro/FETCH_MOBILEINTRO_LIST',
  FETCH_MOBILEINTRO: 'mobileIntro/FETCH_MOBILEINTRO',
  CREATE_MOBILEINTRO: 'mobileIntro/CREATE_MOBILEINTRO',
  UPDATE_MOBILEINTRO: 'mobileIntro/UPDATE_MOBILEINTRO',
  DELETE_MOBILEINTRO: 'mobileIntro/DELETE_MOBILEINTRO',
  RESET: 'mobileIntro/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMobileIntro>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MobileIntroState = Readonly<typeof initialState>;

// Reducer

export default (state: MobileIntroState = initialState, action): MobileIntroState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MOBILEINTRO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOBILEINTRO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MOBILEINTRO):
    case REQUEST(ACTION_TYPES.UPDATE_MOBILEINTRO):
    case REQUEST(ACTION_TYPES.DELETE_MOBILEINTRO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MOBILEINTRO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOBILEINTRO):
    case FAILURE(ACTION_TYPES.CREATE_MOBILEINTRO):
    case FAILURE(ACTION_TYPES.UPDATE_MOBILEINTRO):
    case FAILURE(ACTION_TYPES.DELETE_MOBILEINTRO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOBILEINTRO_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_MOBILEINTRO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOBILEINTRO):
    case SUCCESS(ACTION_TYPES.UPDATE_MOBILEINTRO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOBILEINTRO):
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

const apiUrl = 'api/mobile-intros';
const getApiUrl = 'api/getMobileIntros';

// Actions

export const getEntities: ICrudGetAllAction<IMobileIntro> = (page, size, sort) => {
  const requestUrl = `${getApiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MOBILEINTRO_LIST,
    payload: axios.get<IMobileIntro>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMobileIntro> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOBILEINTRO,
    payload: axios.get<IMobileIntro>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMobileIntro> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOBILEINTRO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IMobileIntro> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOBILEINTRO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMobileIntro> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOBILEINTRO,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
