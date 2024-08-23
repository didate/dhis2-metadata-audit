import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramRuleAction } from '../program-rule-action.model';

@Component({
  selector: 'jhi-program-rule-action-detail',
  templateUrl: './program-rule-action-detail.component.html',
})
export class ProgramRuleActionDetailComponent implements OnInit {
  programRuleAction: IProgramRuleAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRuleAction }) => {
      this.programRuleAction = programRuleAction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
