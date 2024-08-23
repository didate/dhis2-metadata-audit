import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProgramStage } from '../program-stage.model';
import { ProgramStageService } from '../service/program-stage.service';

const programStageResolve = (route: ActivatedRouteSnapshot): Observable<null | IProgramStage> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProgramStageService)
      .find(id)
      .pipe(
        mergeMap((programStage: HttpResponse<IProgramStage>) => {
          if (programStage.body) {
            return of(programStage.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default programStageResolve;
