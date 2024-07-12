import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '5ce95a33-0a63-4367-9d79-9a59dd55aed3',
};

export const sampleWithPartialData: IAuthority = {
  name: '01cb18bc-c755-464a-aaae-2b648c48d4f7',
};

export const sampleWithFullData: IAuthority = {
  name: 'ff7380fb-05da-4231-b89a-c84109dcd680',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
