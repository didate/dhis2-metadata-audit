import { Component, OnInit } from '@angular/core';
import { IProgramRule } from '../program-rule.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { ProgramRuleService, EntityArrayResponseType } from '../service/program-rule.service';

@Component({
  selector: 'jhi-program-rule-history',
  templateUrl: './program-rule-history.component.html',
  styleUrls: ['./program-rule-history.component.scss'],
})
export class ProgramRuleHistoryComponent implements OnInit {
  programRule: IProgramRule | null = null;
  programRules?: IProgramRule[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected programRuleService: ProgramRuleService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IProgramRule): string => this.programRuleService.getProgramRuleIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRule }) => {
      this.programRule = programRule;
      this.load();
    });
  }

  load(): void {
    this.programRuleService
      .history(this.programRule?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.programRules = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(programRule: IProgramRule) {
    if (this.setRev.length < 2 && programRule.isSelected) {
      this.setRev.push(programRule.revisionNumber as number);
    } else if (!programRule.isSelected) {
      const index = this.setRev.indexOf(programRule.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      programRule.isSelected = false;
    }
  }
}
