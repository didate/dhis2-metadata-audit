import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionGroup } from '../option-group.model';
import { EntityArrayResponseType, OptionGroupService } from '../service/option-group.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-option-group-detail',
  templateUrl: './option-group-detail.component.html',
})
export class OptionGroupDetailComponent implements OnInit {
  optionGroup: IOptionGroup | null = null;

  optionGroupRev1: IOptionGroup | null = null;
  optionGroupRev2: IOptionGroup | null = null;

  optionGroups?: IOptionGroup[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected optionGroupService: OptionGroupService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ optionGroup }) => {
        this.optionGroup = optionGroup;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.optionGroup) {
      this.optionGroupService
        .compare(this.optionGroup.id, rev1, rev2)
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
    this.optionGroups = dataFromBody;
    this.optionGroupRev1 = this.optionGroups[0];
    this.optionGroupRev2 = this.optionGroups[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IOptionGroup[] | null): IOptionGroup[] {
    return data ?? [];
  }
}
