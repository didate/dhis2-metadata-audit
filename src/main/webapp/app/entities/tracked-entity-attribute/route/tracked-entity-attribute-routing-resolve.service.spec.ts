import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';

import { TrackedEntityAttributeRoutingResolveService } from './tracked-entity-attribute-routing-resolve.service';

describe('TrackedEntityAttribute routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TrackedEntityAttributeRoutingResolveService;
  let service: TrackedEntityAttributeService;
  let resultTrackedEntityAttribute: ITrackedEntityAttribute | null | undefined;

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
    routingResolveService = TestBed.inject(TrackedEntityAttributeRoutingResolveService);
    service = TestBed.inject(TrackedEntityAttributeService);
    resultTrackedEntityAttribute = undefined;
  });

  describe('resolve', () => {
    it('should return ITrackedEntityAttribute returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrackedEntityAttribute = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTrackedEntityAttribute).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrackedEntityAttribute = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTrackedEntityAttribute).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITrackedEntityAttribute>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTrackedEntityAttribute = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTrackedEntityAttribute).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
