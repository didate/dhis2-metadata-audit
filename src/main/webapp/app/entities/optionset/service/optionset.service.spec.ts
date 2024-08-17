import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOptionset } from '../optionset.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../optionset.test-samples';

import { OptionsetService } from './optionset.service';

const requireRestSample: IOptionset = {
  ...sampleWithRequiredData,
};

describe('Optionset Service', () => {
  let service: OptionsetService;
  let httpMock: HttpTestingController;
  let expectedResult: IOptionset | IOptionset[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OptionsetService);
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

    it('should create a Optionset', () => {
      const optionset = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(optionset).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Optionset', () => {
      const optionset = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(optionset).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Optionset', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Optionset', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Optionset', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOptionsetToCollectionIfMissing', () => {
      it('should add a Optionset to an empty array', () => {
        const optionset: IOptionset = sampleWithRequiredData;
        expectedResult = service.addOptionsetToCollectionIfMissing([], optionset);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(optionset);
      });

      it('should not add a Optionset to an array that contains it', () => {
        const optionset: IOptionset = sampleWithRequiredData;
        const optionsetCollection: IOptionset[] = [
          {
            ...optionset,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOptionsetToCollectionIfMissing(optionsetCollection, optionset);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Optionset to an array that doesn't contain it", () => {
        const optionset: IOptionset = sampleWithRequiredData;
        const optionsetCollection: IOptionset[] = [sampleWithPartialData];
        expectedResult = service.addOptionsetToCollectionIfMissing(optionsetCollection, optionset);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(optionset);
      });

      it('should add only unique Optionset to an array', () => {
        const optionsetArray: IOptionset[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const optionsetCollection: IOptionset[] = [sampleWithRequiredData];
        expectedResult = service.addOptionsetToCollectionIfMissing(optionsetCollection, ...optionsetArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const optionset: IOptionset = sampleWithRequiredData;
        const optionset2: IOptionset = sampleWithPartialData;
        expectedResult = service.addOptionsetToCollectionIfMissing([], optionset, optionset2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(optionset);
        expect(expectedResult).toContain(optionset2);
      });

      it('should accept null and undefined values', () => {
        const optionset: IOptionset = sampleWithRequiredData;
        expectedResult = service.addOptionsetToCollectionIfMissing([], null, optionset, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(optionset);
      });

      it('should return initial array if no Optionset is added', () => {
        const optionsetCollection: IOptionset[] = [sampleWithRequiredData];
        expectedResult = service.addOptionsetToCollectionIfMissing(optionsetCollection, undefined, null);
        expect(expectedResult).toEqual(optionsetCollection);
      });
    });

    describe('compareOptionset', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOptionset(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareOptionset(entity1, entity2);
        const compareResult2 = service.compareOptionset(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareOptionset(entity1, entity2);
        const compareResult2 = service.compareOptionset(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareOptionset(entity1, entity2);
        const compareResult2 = service.compareOptionset(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
