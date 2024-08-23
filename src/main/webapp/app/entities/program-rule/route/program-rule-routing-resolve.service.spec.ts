import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';

import programRuleResolve from './program-rule-routing-resolve.service';

describe('ProgramRule routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ProgramRuleService;
  let resultProgramRule: IProgramRule | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(ProgramRuleService);
    resultProgramRule = undefined;
  });

  describe('resolve', () => {
    it('should return IProgramRule returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        programRuleResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultProgramRule = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultProgramRule).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        programRuleResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultProgramRule = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProgramRule).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProgramRule>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        programRuleResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultProgramRule = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultProgramRule).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
