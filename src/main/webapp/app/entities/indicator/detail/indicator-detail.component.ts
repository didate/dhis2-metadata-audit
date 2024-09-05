import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicator } from '../indicator.model';
import { EntityArrayResponseType, IndicatorService } from '../service/indicator.service';
import { tap } from 'rxjs';
import { DiffService } from 'app/entities/diff.service';

@Component({
  selector: 'jhi-indicator-detail',
  templateUrl: './indicator-detail.component.html',
})
export class IndicatorDetailComponent implements OnInit {
  indicator: IIndicator | null = null;
  indicatorRev1: IIndicator | null = null;
  indicatorRev2: IIndicator | null = null;

  indicators?: IIndicator[];
  isLoading = false;

  rev1: number = 0;
  rev2: number = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected indicatorService: IndicatorService, private diffService: DiffService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ indicator }) => {
        this.indicator = indicator;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    this.indicatorService
      .compare(this.indicator?.id!, rev1, rev2)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  showDiff(text1: any, text2: any) {
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
    this.indicators = dataFromBody;
    this.indicatorRev1 = this.indicators[0];
    this.indicatorRev2 = this.indicators[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IIndicator[] | null): IIndicator[] {
    return data ?? [];
  }
}
