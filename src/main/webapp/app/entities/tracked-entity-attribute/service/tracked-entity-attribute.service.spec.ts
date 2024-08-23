import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../tracked-entity-attribute.test-samples';

import { TrackedEntityAttributeService, RestTrackedEntityAttribute } from './tracked-entity-attribute.service';

const requireRestSample: RestTrackedEntityAttribute = {
  ...sampleWithRequiredData,
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
  created: sampleWithRequiredData.created?.toJSON(),
};

describe('TrackedEntityAttribute Service', () => {
  let service: TrackedEntityAttributeService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrackedEntityAttribute | ITrackedEntityAttribute[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrackedEntityAttributeService);
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

    it('should create a TrackedEntityAttribute', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trackedEntityAttribute = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trackedEntityAttribute).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrackedEntityAttribute', () => {
      const trackedEntityAttribute = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trackedEntityAttribute).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrackedEntityAttribute', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrackedEntityAttribute', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrackedEntityAttribute', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrackedEntityAttributeToCollectionIfMissing', () => {
      it('should add a TrackedEntityAttribute to an empty array', () => {
        const trackedEntityAttribute: ITrackedEntityAttribute = sampleWithRequiredData;
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing([], trackedEntityAttribute);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trackedEntityAttribute);
      });

      it('should not add a TrackedEntityAttribute to an array that contains it', () => {
        const trackedEntityAttribute: ITrackedEntityAttribute = sampleWithRequiredData;
        const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [
          {
            ...trackedEntityAttribute,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing(trackedEntityAttributeCollection, trackedEntityAttribute);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrackedEntityAttribute to an array that doesn't contain it", () => {
        const trackedEntityAttribute: ITrackedEntityAttribute = sampleWithRequiredData;
        const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [sampleWithPartialData];
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing(trackedEntityAttributeCollection, trackedEntityAttribute);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trackedEntityAttribute);
      });

      it('should add only unique TrackedEntityAttribute to an array', () => {
        const trackedEntityAttributeArray: ITrackedEntityAttribute[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [sampleWithRequiredData];
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing(
          trackedEntityAttributeCollection,
          ...trackedEntityAttributeArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trackedEntityAttribute: ITrackedEntityAttribute = sampleWithRequiredData;
        const trackedEntityAttribute2: ITrackedEntityAttribute = sampleWithPartialData;
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing([], trackedEntityAttribute, trackedEntityAttribute2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trackedEntityAttribute);
        expect(expectedResult).toContain(trackedEntityAttribute2);
      });

      it('should accept null and undefined values', () => {
        const trackedEntityAttribute: ITrackedEntityAttribute = sampleWithRequiredData;
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing([], null, trackedEntityAttribute, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trackedEntityAttribute);
      });

      it('should return initial array if no TrackedEntityAttribute is added', () => {
        const trackedEntityAttributeCollection: ITrackedEntityAttribute[] = [sampleWithRequiredData];
        expectedResult = service.addTrackedEntityAttributeToCollectionIfMissing(trackedEntityAttributeCollection, undefined, null);
        expect(expectedResult).toEqual(trackedEntityAttributeCollection);
      });
    });

    describe('compareTrackedEntityAttribute', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrackedEntityAttribute(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareTrackedEntityAttribute(entity1, entity2);
        const compareResult2 = service.compareTrackedEntityAttribute(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareTrackedEntityAttribute(entity1, entity2);
        const compareResult2 = service.compareTrackedEntityAttribute(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareTrackedEntityAttribute(entity1, entity2);
        const compareResult2 = service.compareTrackedEntityAttribute(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
