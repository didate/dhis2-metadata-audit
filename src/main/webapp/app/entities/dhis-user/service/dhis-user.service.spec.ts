import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDHISUser } from '../dhis-user.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../dhis-user.test-samples';

import { DHISUserService, RestDHISUser } from './dhis-user.service';

const requireRestSample: RestDHISUser = {
  ...sampleWithRequiredData,
  lastLogin: sampleWithRequiredData.lastLogin?.toJSON(),
  passwordLastUpdated: sampleWithRequiredData.passwordLastUpdated?.toJSON(),
  created: sampleWithRequiredData.created?.toJSON(),
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
};

describe('DHISUser Service', () => {
  let service: DHISUserService;
  let httpMock: HttpTestingController;
  let expectedResult: IDHISUser | IDHISUser[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DHISUserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DHISUser', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dHISUser = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dHISUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DHISUser', () => {
      const dHISUser = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dHISUser).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DHISUser', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DHISUser', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DHISUser', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDHISUserToCollectionIfMissing', () => {
      it('should add a DHISUser to an empty array', () => {
        const dHISUser: IDHISUser = sampleWithRequiredData;
        expectedResult = service.addDHISUserToCollectionIfMissing([], dHISUser);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dHISUser);
      });

      it('should not add a DHISUser to an array that contains it', () => {
        const dHISUser: IDHISUser = sampleWithRequiredData;
        const dHISUserCollection: IDHISUser[] = [
          {
            ...dHISUser,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDHISUserToCollectionIfMissing(dHISUserCollection, dHISUser);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DHISUser to an array that doesn't contain it", () => {
        const dHISUser: IDHISUser = sampleWithRequiredData;
        const dHISUserCollection: IDHISUser[] = [sampleWithPartialData];
        expectedResult = service.addDHISUserToCollectionIfMissing(dHISUserCollection, dHISUser);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dHISUser);
      });

      it('should add only unique DHISUser to an array', () => {
        const dHISUserArray: IDHISUser[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dHISUserCollection: IDHISUser[] = [sampleWithRequiredData];
        expectedResult = service.addDHISUserToCollectionIfMissing(dHISUserCollection, ...dHISUserArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dHISUser: IDHISUser = sampleWithRequiredData;
        const dHISUser2: IDHISUser = sampleWithPartialData;
        expectedResult = service.addDHISUserToCollectionIfMissing([], dHISUser, dHISUser2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dHISUser);
        expect(expectedResult).toContain(dHISUser2);
      });

      it('should accept null and undefined values', () => {
        const dHISUser: IDHISUser = sampleWithRequiredData;
        expectedResult = service.addDHISUserToCollectionIfMissing([], null, dHISUser, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dHISUser);
      });

      it('should return initial array if no DHISUser is added', () => {
        const dHISUserCollection: IDHISUser[] = [sampleWithRequiredData];
        expectedResult = service.addDHISUserToCollectionIfMissing(dHISUserCollection, undefined, null);
        expect(expectedResult).toEqual(dHISUserCollection);
      });
    });

    describe('compareDHISUser', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDHISUser(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareDHISUser(entity1, entity2);
        const compareResult2 = service.compareDHISUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareDHISUser(entity1, entity2);
        const compareResult2 = service.compareDHISUser(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareDHISUser(entity1, entity2);
        const compareResult2 = service.compareDHISUser(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
