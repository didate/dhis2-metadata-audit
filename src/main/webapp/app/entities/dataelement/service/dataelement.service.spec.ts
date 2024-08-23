import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDataelement } from '../dataelement.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../dataelement.test-samples';

import { DataelementService, RestDataelement } from './dataelement.service';

const requireRestSample: RestDataelement = {
  ...sampleWithRequiredData,
  created: sampleWithRequiredData.created?.toJSON(),
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
};

describe('Dataelement Service', () => {
  let service: DataelementService;
  let httpMock: HttpTestingController;
  let expectedResult: IDataelement | IDataelement[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DataelementService);
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

    it('should create a Dataelement', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dataelement = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dataelement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dataelement', () => {
      const dataelement = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dataelement).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Dataelement', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Dataelement', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Dataelement', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDataelementToCollectionIfMissing', () => {
      it('should add a Dataelement to an empty array', () => {
        const dataelement: IDataelement = sampleWithRequiredData;
        expectedResult = service.addDataelementToCollectionIfMissing([], dataelement);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataelement);
      });

      it('should not add a Dataelement to an array that contains it', () => {
        const dataelement: IDataelement = sampleWithRequiredData;
        const dataelementCollection: IDataelement[] = [
          {
            ...dataelement,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDataelementToCollectionIfMissing(dataelementCollection, dataelement);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dataelement to an array that doesn't contain it", () => {
        const dataelement: IDataelement = sampleWithRequiredData;
        const dataelementCollection: IDataelement[] = [sampleWithPartialData];
        expectedResult = service.addDataelementToCollectionIfMissing(dataelementCollection, dataelement);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataelement);
      });

      it('should add only unique Dataelement to an array', () => {
        const dataelementArray: IDataelement[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dataelementCollection: IDataelement[] = [sampleWithRequiredData];
        expectedResult = service.addDataelementToCollectionIfMissing(dataelementCollection, ...dataelementArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dataelement: IDataelement = sampleWithRequiredData;
        const dataelement2: IDataelement = sampleWithPartialData;
        expectedResult = service.addDataelementToCollectionIfMissing([], dataelement, dataelement2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataelement);
        expect(expectedResult).toContain(dataelement2);
      });

      it('should accept null and undefined values', () => {
        const dataelement: IDataelement = sampleWithRequiredData;
        expectedResult = service.addDataelementToCollectionIfMissing([], null, dataelement, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataelement);
      });

      it('should return initial array if no Dataelement is added', () => {
        const dataelementCollection: IDataelement[] = [sampleWithRequiredData];
        expectedResult = service.addDataelementToCollectionIfMissing(dataelementCollection, undefined, null);
        expect(expectedResult).toEqual(dataelementCollection);
      });
    });

    describe('compareDataelement', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDataelement(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareDataelement(entity1, entity2);
        const compareResult2 = service.compareDataelement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareDataelement(entity1, entity2);
        const compareResult2 = service.compareDataelement(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareDataelement(entity1, entity2);
        const compareResult2 = service.compareDataelement(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
