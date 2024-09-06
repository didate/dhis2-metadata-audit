import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramRuleVariable } from '../program-rule-variable.model';
import { EntityArrayResponseType, ProgramRuleVariableService } from '../service/program-rule-variable.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-program-rule-variable-detail',
  templateUrl: './program-rule-variable-detail.component.html',
})
export class ProgramRuleVariableDetailComponent implements OnInit {
  programRuleVariable: IProgramRuleVariable | null = null;

  programRuleVariableRev1: IProgramRuleVariable | null = null;
  programRuleVariableRev2: IProgramRuleVariable | null = null;

  programRuleVariables?: IProgramRuleVariable[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected programRuleVariableService: ProgramRuleVariableService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ programRuleVariable }) => {
        this.programRuleVariable = programRuleVariable;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.programRuleVariable) {
      this.programRuleVariableService
        .compare(this.programRuleVariable.id, rev1, rev2)
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
    this.programRuleVariables = dataFromBody;
    this.programRuleVariableRev1 = this.programRuleVariables[0];
    this.programRuleVariableRev2 = this.programRuleVariables[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IProgramRuleVariable[] | null): IProgramRuleVariable[] {
    return data ?? [];
  }
}
