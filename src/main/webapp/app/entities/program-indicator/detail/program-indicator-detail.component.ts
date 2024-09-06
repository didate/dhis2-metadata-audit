import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramIndicator } from '../program-indicator.model';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';
import { ProgramIndicatorService, EntityArrayResponseType } from '../service/program-indicator.service';

@Component({
  selector: 'jhi-program-indicator-detail',
  templateUrl: './program-indicator-detail.component.html',
})
export class ProgramIndicatorDetailComponent implements OnInit {
  programIndicator: IProgramIndicator | null = null;

  programIndicatorRev1: IProgramIndicator | null = null;
  programIndicatorRev2: IProgramIndicator | null = null;

  programIndicators?: IProgramIndicator[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected programIndicatorService: ProgramIndicatorService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ programIndicator }) => {
        this.programIndicator = programIndicator;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.programIndicator) {
      this.programIndicatorService
        .compare(this.programIndicator.id, rev1, rev2)
        .pipe(tap(() => (this.isLoading = false)))
        .subscribe({
          next: (res: EntityArrayResponseType) => {
            this.onResponseSuccess(res);
          },
        });
    }
  }

  showDiff(text1: any, text2: any): any {
    if (text1) {
      return this.diffService.generateDiff(text1 as string, text2 as string);
    }
    return text1;
  }

  previousState(): void {
    window.history.back();
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.programIndicators = dataFromBody;
    this.programIndicatorRev1 = this.programIndicators[0];
    this.programIndicatorRev2 = this.programIndicators[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IProgramIndicator[] | null): IProgramIndicator[] {
    return data ?? [];
  }
}
