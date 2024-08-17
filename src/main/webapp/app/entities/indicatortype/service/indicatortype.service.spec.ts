import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IIndicatortype } from '../indicatortype.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../indicatortype.test-samples';

import { IndicatortypeService } from './indicatortype.service';

const requireRestSample: IIndicatortype = {
  ...sampleWithRequiredData,
};

describe('Indicatortype Service', () => {
  let service: IndicatortypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IIndicatortype | IIndicatortype[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(IndicatortypeService);
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

    it('should create a Indicatortype', () => {
      const indicatortype = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(indicatortype).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Indicatortype', () => {
      const indicatortype = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(indicatortype).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Indicatortype', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Indicatortype', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Indicatortype', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIndicatortypeToCollectionIfMissing', () => {
      it('should add a Indicatortype to an empty array', () => {
        const indicatortype: IIndicatortype = sampleWithRequiredData;
        expectedResult = service.addIndicatortypeToCollectionIfMissing([], indicatortype);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(indicatortype);
      });

      it('should not add a Indicatortype to an array that contains it', () => {
        const indicatortype: IIndicatortype = sampleWithRequiredData;
        const indicatortypeCollection: IIndicatortype[] = [
          {
            ...indicatortype,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIndicatortypeToCollectionIfMissing(indicatortypeCollection, indicatortype);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Indicatortype to an array that doesn't contain it", () => {
        const indicatortype: IIndicatortype = sampleWithRequiredData;
        const indicatortypeCollection: IIndicatortype[] = [sampleWithPartialData];
        expectedResult = service.addIndicatortypeToCollectionIfMissing(indicatortypeCollection, indicatortype);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(indicatortype);
      });

      it('should add only unique Indicatortype to an array', () => {
        const indicatortypeArray: IIndicatortype[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const indicatortypeCollection: IIndicatortype[] = [sampleWithRequiredData];
        expectedResult = service.addIndicatortypeToCollectionIfMissing(indicatortypeCollection, ...indicatortypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const indicatortype: IIndicatortype = sampleWithRequiredData;
        const indicatortype2: IIndicatortype = sampleWithPartialData;
        expectedResult = service.addIndicatortypeToCollectionIfMissing([], indicatortype, indicatortype2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(indicatortype);
        expect(expectedResult).toContain(indicatortype2);
      });

      it('should accept null and undefined values', () => {
        const indicatortype: IIndicatortype = sampleWithRequiredData;
        expectedResult = service.addIndicatortypeToCollectionIfMissing([], null, indicatortype, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(indicatortype);
      });

      it('should return initial array if no Indicatortype is added', () => {
        const indicatortypeCollection: IIndicatortype[] = [sampleWithRequiredData];
        expectedResult = service.addIndicatortypeToCollectionIfMissing(indicatortypeCollection, undefined, null);
        expect(expectedResult).toEqual(indicatortypeCollection);
      });
    });

    describe('compareIndicatortype', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIndicatortype(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareIndicatortype(entity1, entity2);
        const compareResult2 = service.compareIndicatortype(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareIndicatortype(entity1, entity2);
        const compareResult2 = service.compareIndicatortype(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareIndicatortype(entity1, entity2);
        const compareResult2 = service.compareIndicatortype(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
