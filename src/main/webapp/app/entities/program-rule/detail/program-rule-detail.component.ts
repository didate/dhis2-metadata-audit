import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramRule } from '../program-rule.model';

@Component({
  selector: 'jhi-program-rule-detail',
  templateUrl: './program-rule-detail.component.html',
})
export class ProgramRuleDetailComponent implements OnInit {
  programRule: IProgramRule | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRule }) => {
      this.programRule = programRule;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
