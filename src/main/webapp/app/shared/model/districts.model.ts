export interface IDistricts {
  id?: number;
  name?: string;
  bn_name?: string;
  lat?: string;
  lon?: string;
  url?: string;
  divisionsId?: number;
}

export const defaultValue: Readonly<IDistricts> = {};
