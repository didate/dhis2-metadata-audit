import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionset } from '../optionset.model';
import { EntityArrayResponseType, OptionsetService } from '../service/optionset.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-optionset-detail',
  templateUrl: './optionset-detail.component.html',
})
export class OptionsetDetailComponent implements OnInit {
  optionset: IOptionset | null = null;

  optionsetRev1: IOptionset | null = null;
  optionsetRev2: IOptionset | null = null;

  optionsets?: IOptionset[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected optionsetService: OptionsetService, private diffService: DiffService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ optionset }) => {
        this.optionset = optionset;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.optionset) {
      this.optionsetService
        .compare(this.optionset.id, rev1, rev2)
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
    this.optionsets = dataFromBody;
    this.optionsetRev1 = this.optionsets[0];
    this.optionsetRev2 = this.optionsets[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IOptionset[] | null): IOptionset[] {
    return data ?? [];
  }
}
