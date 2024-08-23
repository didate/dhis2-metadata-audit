import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorycombo } from '../categorycombo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../categorycombo.test-samples';

import { CategorycomboService } from './categorycombo.service';

const requireRestSample: ICategorycombo = {
  ...sampleWithRequiredData,
};

describe('Categorycombo Service', () => {
  let service: CategorycomboService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategorycombo | ICategorycombo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategorycomboService);
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

    it('should create a Categorycombo', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categorycombo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categorycombo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Categorycombo', () => {
      const categorycombo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categorycombo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Categorycombo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Categorycombo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Categorycombo', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategorycomboToCollectionIfMissing', () => {
      it('should add a Categorycombo to an empty array', () => {
        const categorycombo: ICategorycombo = sampleWithRequiredData;
        expectedResult = service.addCategorycomboToCollectionIfMissing([], categorycombo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorycombo);
      });

      it('should not add a Categorycombo to an array that contains it', () => {
        const categorycombo: ICategorycombo = sampleWithRequiredData;
        const categorycomboCollection: ICategorycombo[] = [
          {
            ...categorycombo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategorycomboToCollectionIfMissing(categorycomboCollection, categorycombo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Categorycombo to an array that doesn't contain it", () => {
        const categorycombo: ICategorycombo = sampleWithRequiredData;
        const categorycomboCollection: ICategorycombo[] = [sampleWithPartialData];
        expectedResult = service.addCategorycomboToCollectionIfMissing(categorycomboCollection, categorycombo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorycombo);
      });

      it('should add only unique Categorycombo to an array', () => {
        const categorycomboArray: ICategorycombo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categorycomboCollection: ICategorycombo[] = [sampleWithRequiredData];
        expectedResult = service.addCategorycomboToCollectionIfMissing(categorycomboCollection, ...categorycomboArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categorycombo: ICategorycombo = sampleWithRequiredData;
        const categorycombo2: ICategorycombo = sampleWithPartialData;
        expectedResult = service.addCategorycomboToCollectionIfMissing([], categorycombo, categorycombo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categorycombo);
        expect(expectedResult).toContain(categorycombo2);
      });

      it('should accept null and undefined values', () => {
        const categorycombo: ICategorycombo = sampleWithRequiredData;
        expectedResult = service.addCategorycomboToCollectionIfMissing([], null, categorycombo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categorycombo);
      });

      it('should return initial array if no Categorycombo is added', () => {
        const categorycomboCollection: ICategorycombo[] = [sampleWithRequiredData];
        expectedResult = service.addCategorycomboToCollectionIfMissing(categorycomboCollection, undefined, null);
        expect(expectedResult).toEqual(categorycomboCollection);
      });
    });

    describe('compareCategorycombo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategorycombo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCategorycombo(entity1, entity2);
        const compareResult2 = service.compareCategorycombo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareCategorycombo(entity1, entity2);
        const compareResult2 = service.compareCategorycombo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareCategorycombo(entity1, entity2);
        const compareResult2 = service.compareCategorycombo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
