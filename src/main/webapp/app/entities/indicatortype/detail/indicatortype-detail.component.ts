import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicatortype } from '../indicatortype.model';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';
import { EntityArrayResponseType, IndicatortypeService } from '../service/indicatortype.service';

@Component({
  selector: 'jhi-indicatortype-detail',
  templateUrl: './indicatortype-detail.component.html',
})
export class IndicatortypeDetailComponent implements OnInit {
  indicatortype: IIndicatortype | null = null;

  indicatortypeRev1: IIndicatortype | null = null;
  indicatortypeRev2: IIndicatortype | null = null;

  indicatortypes?: IIndicatortype[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected indicatortypeService: IndicatortypeService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ indicatortype }) => {
        this.indicatortype = indicatortype;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.indicatortype) {
      this.indicatortypeService
        .compare(this.indicatortype.id, rev1, rev2)
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
    this.indicatortypes = dataFromBody;
    this.indicatortypeRev1 = this.indicatortypes[0];
    this.indicatortypeRev2 = this.indicatortypes[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IIndicatortype[] | null): IIndicatortype[] {
    return data ?? [];
  }
}
