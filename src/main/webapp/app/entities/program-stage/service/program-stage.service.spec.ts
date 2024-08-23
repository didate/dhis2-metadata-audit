import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProgramStage } from '../program-stage.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../program-stage.test-samples';

import { ProgramStageService, RestProgramStage } from './program-stage.service';

const requireRestSample: RestProgramStage = {
  ...sampleWithRequiredData,
  created: sampleWithRequiredData.created?.toJSON(),
  lastUpdated: sampleWithRequiredData.lastUpdated?.toJSON(),
};

describe('ProgramStage Service', () => {
  let service: ProgramStageService;
  let httpMock: HttpTestingController;
  let expectedResult: IProgramStage | IProgramStage[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProgramStageService);
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

    it('should create a ProgramStage', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const programStage = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(programStage).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProgramStage', () => {
      const programStage = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(programStage).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProgramStage', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProgramStage', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProgramStage', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProgramStageToCollectionIfMissing', () => {
      it('should add a ProgramStage to an empty array', () => {
        const programStage: IProgramStage = sampleWithRequiredData;
        expectedResult = service.addProgramStageToCollectionIfMissing([], programStage);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programStage);
      });

      it('should not add a ProgramStage to an array that contains it', () => {
        const programStage: IProgramStage = sampleWithRequiredData;
        const programStageCollection: IProgramStage[] = [
          {
            ...programStage,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProgramStageToCollectionIfMissing(programStageCollection, programStage);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProgramStage to an array that doesn't contain it", () => {
        const programStage: IProgramStage = sampleWithRequiredData;
        const programStageCollection: IProgramStage[] = [sampleWithPartialData];
        expectedResult = service.addProgramStageToCollectionIfMissing(programStageCollection, programStage);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programStage);
      });

      it('should add only unique ProgramStage to an array', () => {
        const programStageArray: IProgramStage[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const programStageCollection: IProgramStage[] = [sampleWithRequiredData];
        expectedResult = service.addProgramStageToCollectionIfMissing(programStageCollection, ...programStageArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const programStage: IProgramStage = sampleWithRequiredData;
        const programStage2: IProgramStage = sampleWithPartialData;
        expectedResult = service.addProgramStageToCollectionIfMissing([], programStage, programStage2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(programStage);
        expect(expectedResult).toContain(programStage2);
      });

      it('should accept null and undefined values', () => {
        const programStage: IProgramStage = sampleWithRequiredData;
        expectedResult = service.addProgramStageToCollectionIfMissing([], null, programStage, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(programStage);
      });

      it('should return initial array if no ProgramStage is added', () => {
        const programStageCollection: IProgramStage[] = [sampleWithRequiredData];
        expectedResult = service.addProgramStageToCollectionIfMissing(programStageCollection, undefined, null);
        expect(expectedResult).toEqual(programStageCollection);
      });
    });

    describe('compareProgramStage', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProgramStage(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareProgramStage(entity1, entity2);
        const compareResult2 = service.compareProgramStage(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareProgramStage(entity1, entity2);
        const compareResult2 = service.compareProgramStage(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareProgramStage(entity1, entity2);
        const compareResult2 = service.compareProgramStage(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
