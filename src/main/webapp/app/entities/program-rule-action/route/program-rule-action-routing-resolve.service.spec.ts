import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';

import { ProgramRuleActionRoutingResolveService } from './program-rule-action-routing-resolve.service';

describe('ProgramRuleAction routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ProgramRuleActionRoutingResolveService;
  let service: ProgramRuleActionService;
  let resultProgramRuleAction: IProgramRuleAction | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
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
    routingResolveService = TestBed.inject(ProgramRuleActionRoutingResolveService);
    service = TestBed.inject(ProgramRuleActionService);
    resultProgramRuleAction = undefined;
  });

  describe('resolve', () => {
    it('should return IProgramRuleAction returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProgramRuleAction = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProgramRuleAction).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProgramRuleAction = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultProgramRuleAction).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IProgramRuleAction>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultProgramRuleAction = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultProgramRuleAction).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
