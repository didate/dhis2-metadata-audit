import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IProgramRuleAction } from '../program-rule-action.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../program-rule-action.test-samples';

import { ProgramRuleActionService, RestProgramRuleAction } from './program-rule-action.service';

const requireRestSample: RestProgramRuleAction = {
  ...sampleWithRequiredData,
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
};

describe('ProgramRuleAction Service', () => {
  let service: ProgramRuleActionService;
  let httpMock: HttpTestingController;
  let expectedResult: IProgramRuleAction | IProgramRuleAction[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ProgramRuleActionService);
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

    it('should create a ProgramRuleAction', () => {
      const programRuleAction = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(programRuleAction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProgramRuleAction', () => {
      const programRuleAction = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(programRuleAction).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProgramRuleAction', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProgramRuleAction', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProgramRuleAction', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProgramRuleActionToCollectionIfMissing', () => {
      it('should add a ProgramRuleAction to an empty array', () => {
        const programRuleAction: IProgramRuleAction = sampleWithRequiredData;
        expectedResult = service.addProgramRuleActionToCollectionIfMissing([], programRuleAction);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programRuleAction);
      });

      it('should not add a ProgramRuleAction to an array that contains it', () => {
        const programRuleAction: IProgramRuleAction = sampleWithRequiredData;
        const programRuleActionCollection: IProgramRuleAction[] = [
          {
            ...programRuleAction,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProgramRuleActionToCollectionIfMissing(programRuleActionCollection, programRuleAction);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProgramRuleAction to an array that doesn't contain it", () => {
        const programRuleAction: IProgramRuleAction = sampleWithRequiredData;
        const programRuleActionCollection: IProgramRuleAction[] = [sampleWithPartialData];
        expectedResult = service.addProgramRuleActionToCollectionIfMissing(programRuleActionCollection, programRuleAction);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programRuleAction);
      });

      it('should add only unique ProgramRuleAction to an array', () => {
        const programRuleActionArray: IProgramRuleAction[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const programRuleActionCollection: IProgramRuleAction[] = [sampleWithRequiredData];
        expectedResult = service.addProgramRuleActionToCollectionIfMissing(programRuleActionCollection, ...programRuleActionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const programRuleAction: IProgramRuleAction = sampleWithRequiredData;
        const programRuleAction2: IProgramRuleAction = sampleWithPartialData;
        expectedResult = service.addProgramRuleActionToCollectionIfMissing([], programRuleAction, programRuleAction2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programRuleAction);
        expect(expectedResult).toContain(programRuleAction2);
      });

      it('should accept null and undefined values', () => {
        const programRuleAction: IProgramRuleAction = sampleWithRequiredData;
        expectedResult = service.addProgramRuleActionToCollectionIfMissing([], null, programRuleAction, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programRuleAction);
      });

      it('should return initial array if no ProgramRuleAction is added', () => {
        const programRuleActionCollection: IProgramRuleAction[] = [sampleWithRequiredData];
        expectedResult = service.addProgramRuleActionToCollectionIfMissing(programRuleActionCollection, undefined, null);
        expect(expectedResult).toEqual(programRuleActionCollection);
      });
    });

    describe('compareProgramRuleAction', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProgramRuleAction(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProgramRuleAction(entity1, entity2);
        const compareResult2 = service.compareProgramRuleAction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProgramRuleAction(entity1, entity2);
        const compareResult2 = service.compareProgramRuleAction(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProgramRuleAction(entity1, entity2);
        const compareResult2 = service.compareProgramRuleAction(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
