import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonNotifier } from '../person-notifier.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../person-notifier.test-samples';

import { PersonNotifierService } from './person-notifier.service';

const requireRestSample: IPersonNotifier = {
  ...sampleWithRequiredData,
};

describe('PersonNotifier Service', () => {
  let service: PersonNotifierService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonNotifier | IPersonNotifier[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonNotifierService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PersonNotifier', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const personNotifier = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personNotifier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonNotifier', () => {
      const personNotifier = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personNotifier).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonNotifier', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonNotifier', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonNotifier', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonNotifierToCollectionIfMissing', () => {
      it('should add a PersonNotifier to an empty array', () => {
        const personNotifier: IPersonNotifier = sampleWithRequiredData;
        expectedResult = service.addPersonNotifierToCollectionIfMissing([], personNotifier);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personNotifier);
      });

      it('should not add a PersonNotifier to an array that contains it', () => {
        const personNotifier: IPersonNotifier = sampleWithRequiredData;
        const personNotifierCollection: IPersonNotifier[] = [
          {
            ...personNotifier,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonNotifierToCollectionIfMissing(personNotifierCollection, personNotifier);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonNotifier to an array that doesn't contain it", () => {
        const personNotifier: IPersonNotifier = sampleWithRequiredData;
        const personNotifierCollection: IPersonNotifier[] = [sampleWithPartialData];
        expectedResult = service.addPersonNotifierToCollectionIfMissing(personNotifierCollection, personNotifier);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personNotifier);
      });

      it('should add only unique PersonNotifier to an array', () => {
        const personNotifierArray: IPersonNotifier[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personNotifierCollection: IPersonNotifier[] = [sampleWithRequiredData];
        expectedResult = service.addPersonNotifierToCollectionIfMissing(personNotifierCollection, ...personNotifierArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personNotifier: IPersonNotifier = sampleWithRequiredData;
        const personNotifier2: IPersonNotifier = sampleWithPartialData;
        expectedResult = service.addPersonNotifierToCollectionIfMissing([], personNotifier, personNotifier2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personNotifier);
        expect(expectedResult).toContain(personNotifier2);
      });

      it('should accept null and undefined values', () => {
        const personNotifier: IPersonNotifier = sampleWithRequiredData;
        expectedResult = service.addPersonNotifierToCollectionIfMissing([], null, personNotifier, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personNotifier);
      });

      it('should return initial array if no PersonNotifier is added', () => {
        const personNotifierCollection: IPersonNotifier[] = [sampleWithRequiredData];
        expectedResult = service.addPersonNotifierToCollectionIfMissing(personNotifierCollection, undefined, null);
        expect(expectedResult).toEqual(personNotifierCollection);
      });
    });

    describe('comparePersonNotifier', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonNotifier(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonNotifier(entity1, entity2);
        const compareResult2 = service.comparePersonNotifier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonNotifier(entity1, entity2);
        const compareResult2 = service.comparePersonNotifier(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonNotifier(entity1, entity2);
        const compareResult2 = service.comparePersonNotifier(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
