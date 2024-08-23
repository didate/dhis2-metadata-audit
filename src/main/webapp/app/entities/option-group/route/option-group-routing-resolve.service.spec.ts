import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IOptionGroup } from '../option-group.model';
import { OptionGroupService } from '../service/option-group.service';

import optionGroupResolve from './option-group-routing-resolve.service';

describe('OptionGroup routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: OptionGroupService;
  let resultOptionGroup: IOptionGroup | null | undefined;

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
    service = TestBed.inject(OptionGroupService);
    resultOptionGroup = undefined;
  });

  describe('resolve', () => {
    it('should return IOptionGroup returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        optionGroupResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOptionGroup = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultOptionGroup).toEqual({ id: 'ABC' });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        optionGroupResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOptionGroup = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultOptionGroup).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IOptionGroup>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 'ABC' };

      // WHEN
      TestBed.runInInjectionContext(() => {
        optionGroupResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultOptionGroup = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith('ABC');
      expect(resultOptionGroup).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
