import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramRule } from '../program-rule.model';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';
import { ProgramRuleService, EntityArrayResponseType } from '../service/program-rule.service';

@Component({
  selector: 'jhi-program-rule-detail',
  templateUrl: './program-rule-detail.component.html',
})
export class ProgramRuleDetailComponent implements OnInit {
  programRule: IProgramRule | null = null;

  programRuleRev1: IProgramRule | null = null;
  programRuleRev2: IProgramRule | null = null;

  programRules?: IProgramRule[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected programRuleService: ProgramRuleService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ programRule }) => {
        this.programRule = programRule;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.programRule) {
      this.programRuleService
        .compare(this.programRule.id, rev1, rev2)
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
    this.programRules = dataFromBody;
    this.programRuleRev1 = this.programRules[0];
    this.programRuleRev2 = this.programRules[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IProgramRule[] | null): IProgramRule[] {
    return data ?? [];
  }
}
