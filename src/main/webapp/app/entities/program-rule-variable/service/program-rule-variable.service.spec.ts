import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProgramRuleVariable } from '../program-rule-variable.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../program-rule-variable.test-samples';

import { ProgramRuleVariableService, RestProgramRuleVariable } from './program-rule-variable.service';

const requireRestSample: RestProgramRuleVariable = {
  ...sampleWithRequiredData,
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
  created: sampleWithRequiredData.created?.toJSON(),
};

describe('ProgramRuleVariable Service', () => {
  let service: ProgramRuleVariableService;
  let httpMock: HttpTestingController;
  let expectedResult: IProgramRuleVariable | IProgramRuleVariable[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProgramRuleVariableService);
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

    it('should create a ProgramRuleVariable', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const programRuleVariable = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(programRuleVariable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProgramRuleVariable', () => {
      const programRuleVariable = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(programRuleVariable).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProgramRuleVariable', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProgramRuleVariable', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProgramRuleVariable', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProgramRuleVariableToCollectionIfMissing', () => {
      it('should add a ProgramRuleVariable to an empty array', () => {
        const programRuleVariable: IProgramRuleVariable = sampleWithRequiredData;
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing([], programRuleVariable);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programRuleVariable);
      });

      it('should not add a ProgramRuleVariable to an array that contains it', () => {
        const programRuleVariable: IProgramRuleVariable = sampleWithRequiredData;
        const programRuleVariableCollection: IProgramRuleVariable[] = [
          {
            ...programRuleVariable,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing(programRuleVariableCollection, programRuleVariable);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProgramRuleVariable to an array that doesn't contain it", () => {
        const programRuleVariable: IProgramRuleVariable = sampleWithRequiredData;
        const programRuleVariableCollection: IProgramRuleVariable[] = [sampleWithPartialData];
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing(programRuleVariableCollection, programRuleVariable);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programRuleVariable);
      });

      it('should add only unique ProgramRuleVariable to an array', () => {
        const programRuleVariableArray: IProgramRuleVariable[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const programRuleVariableCollection: IProgramRuleVariable[] = [sampleWithRequiredData];
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing(programRuleVariableCollection, ...programRuleVariableArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const programRuleVariable: IProgramRuleVariable = sampleWithRequiredData;
        const programRuleVariable2: IProgramRuleVariable = sampleWithPartialData;
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing([], programRuleVariable, programRuleVariable2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programRuleVariable);
        expect(expectedResult).toContain(programRuleVariable2);
      });

      it('should accept null and undefined values', () => {
        const programRuleVariable: IProgramRuleVariable = sampleWithRequiredData;
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing([], null, programRuleVariable, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programRuleVariable);
      });

      it('should return initial array if no ProgramRuleVariable is added', () => {
        const programRuleVariableCollection: IProgramRuleVariable[] = [sampleWithRequiredData];
        expectedResult = service.addProgramRuleVariableToCollectionIfMissing(programRuleVariableCollection, undefined, null);
        expect(expectedResult).toEqual(programRuleVariableCollection);
      });
    });

    describe('compareProgramRuleVariable', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProgramRuleVariable(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProgramRuleVariable(entity1, entity2);
        const compareResult2 = service.compareProgramRuleVariable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProgramRuleVariable(entity1, entity2);
        const compareResult2 = service.compareProgramRuleVariable(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProgramRuleVariable(entity1, entity2);
        const compareResult2 = service.compareProgramRuleVariable(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
