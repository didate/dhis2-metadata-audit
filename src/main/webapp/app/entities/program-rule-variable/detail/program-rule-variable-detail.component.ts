import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramRuleVariable } from '../program-rule-variable.model';

@Component({
  selector: 'jhi-program-rule-variable-detail',
  templateUrl: './program-rule-variable-detail.component.html',
})
export class ProgramRuleVariableDetailComponent implements OnInit {
  programRuleVariable: IProgramRuleVariable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRuleVariable }) => {
      this.programRuleVariable = programRuleVariable;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
