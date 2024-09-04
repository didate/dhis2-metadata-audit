import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService, EntityArrayResponseType } from '../service/program-rule-variable.service';

@Component({
  selector: 'jhi-program-rule-variable-history',
  templateUrl: './program-rule-variable-history.component.html',
  styleUrls: ['./program-rule-variable-history.component.scss'],
})
export class ProgramRuleVariableHistoryComponent implements OnInit {
  programRuleVariable: IProgramRuleVariable | null = null;
  programRuleVariables?: IProgramRuleVariable[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected programRuleVariableService: ProgramRuleVariableService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IProgramRuleVariable): string => this.programRuleVariableService.getProgramRuleVariableIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRuleVariable }) => {
      this.programRuleVariable = programRuleVariable;
      this.load();
    });
  }

  load(): void {
    this.programRuleVariableService
      .history(this.programRuleVariable?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.programRuleVariables = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(programRuleVariable: IProgramRuleVariable) {
    if (this.setRev.length < 2 && programRuleVariable.isSelected) {
      this.setRev.push(programRuleVariable.revisionNumber as number);
    } else if (!programRuleVariable.isSelected) {
      const index = this.setRev.indexOf(programRuleVariable.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      programRuleVariable.isSelected = false;
    }
  }
}
