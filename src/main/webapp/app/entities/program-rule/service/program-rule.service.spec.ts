import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProgramRule } from '../program-rule.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../program-rule.test-samples';

import { ProgramRuleService, RestProgramRule } from './program-rule.service';

const requireRestSample: RestProgramRule = {
  ...sampleWithRequiredData,
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
  created: sampleWithRequiredData.created?.toJSON(),
};

describe('ProgramRule Service', () => {
  let service: ProgramRuleService;
  let httpMock: HttpTestingController;
  let expectedResult: IProgramRule | IProgramRule[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProgramRuleService);
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

    it('should create a ProgramRule', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const programRule = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(programRule).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProgramRule', () => {
      const programRule = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(programRule).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProgramRule', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProgramRule', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProgramRule', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProgramRuleToCollectionIfMissing', () => {
      it('should add a ProgramRule to an empty array', () => {
        const programRule: IProgramRule = sampleWithRequiredData;
        expectedResult = service.addProgramRuleToCollectionIfMissing([], programRule);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programRule);
      });

      it('should not add a ProgramRule to an array that contains it', () => {
        const programRule: IProgramRule = sampleWithRequiredData;
        const programRuleCollection: IProgramRule[] = [
          {
            ...programRule,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProgramRuleToCollectionIfMissing(programRuleCollection, programRule);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProgramRule to an array that doesn't contain it", () => {
        const programRule: IProgramRule = sampleWithRequiredData;
        const programRuleCollection: IProgramRule[] = [sampleWithPartialData];
        expectedResult = service.addProgramRuleToCollectionIfMissing(programRuleCollection, programRule);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programRule);
      });

      it('should add only unique ProgramRule to an array', () => {
        const programRuleArray: IProgramRule[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const programRuleCollection: IProgramRule[] = [sampleWithRequiredData];
        expectedResult = service.addProgramRuleToCollectionIfMissing(programRuleCollection, ...programRuleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const programRule: IProgramRule = sampleWithRequiredData;
        const programRule2: IProgramRule = sampleWithPartialData;
        expectedResult = service.addProgramRuleToCollectionIfMissing([], programRule, programRule2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programRule);
        expect(expectedResult).toContain(programRule2);
      });

      it('should accept null and undefined values', () => {
        const programRule: IProgramRule = sampleWithRequiredData;
        expectedResult = service.addProgramRuleToCollectionIfMissing([], null, programRule, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programRule);
      });

      it('should return initial array if no ProgramRule is added', () => {
        const programRuleCollection: IProgramRule[] = [sampleWithRequiredData];
        expectedResult = service.addProgramRuleToCollectionIfMissing(programRuleCollection, undefined, null);
        expect(expectedResult).toEqual(programRuleCollection);
      });
    });

    describe('compareProgramRule', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProgramRule(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProgramRule(entity1, entity2);
        const compareResult2 = service.compareProgramRule(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProgramRule(entity1, entity2);
        const compareResult2 = service.compareProgramRule(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProgramRule(entity1, entity2);
        const compareResult2 = service.compareProgramRule(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
