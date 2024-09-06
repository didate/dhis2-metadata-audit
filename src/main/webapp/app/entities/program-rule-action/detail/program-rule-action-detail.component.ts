import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramRuleAction } from '../program-rule-action.model';
import { EntityArrayResponseType, ProgramRuleActionService } from '../service/program-rule-action.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-program-rule-action-detail',
  templateUrl: './program-rule-action-detail.component.html',
})
export class ProgramRuleActionDetailComponent implements OnInit {
  programRuleAction: IProgramRuleAction | null = null;

  programRuleActionRev1: IProgramRuleAction | null = null;
  programRuleActionRev2: IProgramRuleAction | null = null;

  programRuleActions?: IProgramRuleAction[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected programRuleActionService: ProgramRuleActionService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ programRuleAction }) => {
        this.programRuleAction = programRuleAction;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.programRuleAction) {
      this.programRuleActionService
        .compare(this.programRuleAction.id, rev1, rev2)
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
    this.programRuleActions = dataFromBody;
    this.programRuleActionRev1 = this.programRuleActions[0];
    this.programRuleActionRev2 = this.programRuleActions[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IProgramRuleAction[] | null): IProgramRuleAction[] {
    return data ?? [];
  }
}
