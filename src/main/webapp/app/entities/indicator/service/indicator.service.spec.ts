import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIndicator } from '../indicator.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../indicator.test-samples';

import { IndicatorService, RestIndicator } from './indicator.service';

const requireRestSample: RestIndicator = {
  ...sampleWithRequiredData,
  created: sampleWithRequiredData.created?.toJSON(),
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
};

describe('Indicator Service', () => {
  let service: IndicatorService;
  let httpMock: HttpTestingController;
  let expectedResult: IIndicator | IIndicator[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IndicatorService);
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

    it('should create a Indicator', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const indicator = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(indicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Indicator', () => {
      const indicator = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(indicator).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Indicator', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Indicator', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Indicator', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIndicatorToCollectionIfMissing', () => {
      it('should add a Indicator to an empty array', () => {
        const indicator: IIndicator = sampleWithRequiredData;
        expectedResult = service.addIndicatorToCollectionIfMissing([], indicator);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(indicator);
      });

      it('should not add a Indicator to an array that contains it', () => {
        const indicator: IIndicator = sampleWithRequiredData;
        const indicatorCollection: IIndicator[] = [
          {
            ...indicator,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIndicatorToCollectionIfMissing(indicatorCollection, indicator);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Indicator to an array that doesn't contain it", () => {
        const indicator: IIndicator = sampleWithRequiredData;
        const indicatorCollection: IIndicator[] = [sampleWithPartialData];
        expectedResult = service.addIndicatorToCollectionIfMissing(indicatorCollection, indicator);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(indicator);
      });

      it('should add only unique Indicator to an array', () => {
        const indicatorArray: IIndicator[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const indicatorCollection: IIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addIndicatorToCollectionIfMissing(indicatorCollection, ...indicatorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const indicator: IIndicator = sampleWithRequiredData;
        const indicator2: IIndicator = sampleWithPartialData;
        expectedResult = service.addIndicatorToCollectionIfMissing([], indicator, indicator2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(indicator);
        expect(expectedResult).toContain(indicator2);
      });

      it('should accept null and undefined values', () => {
        const indicator: IIndicator = sampleWithRequiredData;
        expectedResult = service.addIndicatorToCollectionIfMissing([], null, indicator, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(indicator);
      });

      it('should return initial array if no Indicator is added', () => {
        const indicatorCollection: IIndicator[] = [sampleWithRequiredData];
        expectedResult = service.addIndicatorToCollectionIfMissing(indicatorCollection, undefined, null);
        expect(expectedResult).toEqual(indicatorCollection);
      });
    });

    describe('compareIndicator', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIndicator(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareIndicator(entity1, entity2);
        const compareResult2 = service.compareIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareIndicator(entity1, entity2);
        const compareResult2 = service.compareIndicator(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareIndicator(entity1, entity2);
        const compareResult2 = service.compareIndicator(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
