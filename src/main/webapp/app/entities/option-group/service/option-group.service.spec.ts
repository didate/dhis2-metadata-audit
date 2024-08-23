import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOptionGroup } from '../option-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../option-group.test-samples';

import { OptionGroupService } from './option-group.service';

const requireRestSample: IOptionGroup = {
  ...sampleWithRequiredData,
};

describe('OptionGroup Service', () => {
  let service: OptionGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IOptionGroup | IOptionGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OptionGroupService);
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

    it('should create a OptionGroup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const optionGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(optionGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OptionGroup', () => {
      const optionGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(optionGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OptionGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OptionGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OptionGroup', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOptionGroupToCollectionIfMissing', () => {
      it('should add a OptionGroup to an empty array', () => {
        const optionGroup: IOptionGroup = sampleWithRequiredData;
        expectedResult = service.addOptionGroupToCollectionIfMissing([], optionGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(optionGroup);
      });

      it('should not add a OptionGroup to an array that contains it', () => {
        const optionGroup: IOptionGroup = sampleWithRequiredData;
        const optionGroupCollection: IOptionGroup[] = [
          {
            ...optionGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOptionGroupToCollectionIfMissing(optionGroupCollection, optionGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OptionGroup to an array that doesn't contain it", () => {
        const optionGroup: IOptionGroup = sampleWithRequiredData;
        const optionGroupCollection: IOptionGroup[] = [sampleWithPartialData];
        expectedResult = service.addOptionGroupToCollectionIfMissing(optionGroupCollection, optionGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(optionGroup);
      });

      it('should add only unique OptionGroup to an array', () => {
        const optionGroupArray: IOptionGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const optionGroupCollection: IOptionGroup[] = [sampleWithRequiredData];
        expectedResult = service.addOptionGroupToCollectionIfMissing(optionGroupCollection, ...optionGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const optionGroup: IOptionGroup = sampleWithRequiredData;
        const optionGroup2: IOptionGroup = sampleWithPartialData;
        expectedResult = service.addOptionGroupToCollectionIfMissing([], optionGroup, optionGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(optionGroup);
        expect(expectedResult).toContain(optionGroup2);
      });

      it('should accept null and undefined values', () => {
        const optionGroup: IOptionGroup = sampleWithRequiredData;
        expectedResult = service.addOptionGroupToCollectionIfMissing([], null, optionGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(optionGroup);
      });

      it('should return initial array if no OptionGroup is added', () => {
        const optionGroupCollection: IOptionGroup[] = [sampleWithRequiredData];
        expectedResult = service.addOptionGroupToCollectionIfMissing(optionGroupCollection, undefined, null);
        expect(expectedResult).toEqual(optionGroupCollection);
      });
    });

    describe('compareOptionGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOptionGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareOptionGroup(entity1, entity2);
        const compareResult2 = service.compareOptionGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareOptionGroup(entity1, entity2);
        const compareResult2 = service.compareOptionGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareOptionGroup(entity1, entity2);
        const compareResult2 = service.compareOptionGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
