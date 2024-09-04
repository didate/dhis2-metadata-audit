import { Component, OnInit } from '@angular/core';
import { IProgramRuleAction } from '../program-rule-action.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { EntityArrayResponseType, ProgramRuleActionService } from '../service/program-rule-action.service';

@Component({
  selector: 'jhi-program-rule-action-history',
  templateUrl: './program-rule-action-history.component.html',
  styleUrls: ['./program-rule-action-history.component.scss'],
})
export class ProgramRuleActionHistoryComponent implements OnInit {
  programRuleAction: IProgramRuleAction | null = null;
  programRuleActions?: IProgramRuleAction[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected programRuleActionService: ProgramRuleActionService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IProgramRuleAction): string => this.programRuleActionService.getProgramRuleActionIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRuleAction }) => {
      this.programRuleAction = programRuleAction;
      this.load();
    });
  }

  load(): void {
    this.programRuleActionService
      .history(this.programRuleAction?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.programRuleActions = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(programRuleAction: IProgramRuleAction) {
    if (this.setRev.length < 2 && programRuleAction.isSelected) {
      this.setRev.push(programRuleAction.revisionNumber as number);
    } else if (!programRuleAction.isSelected) {
      const index = this.setRev.indexOf(programRuleAction.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      programRuleAction.isSelected = false;
    }
  }
}
