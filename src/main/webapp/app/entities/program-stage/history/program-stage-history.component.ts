import { Component, OnInit } from '@angular/core';
import { IProgramStage } from '../program-stage.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { ProgramStageService, EntityArrayResponseType } from '../service/program-stage.service';

@Component({
  selector: 'jhi-program-stage-history',
  templateUrl: './program-stage-history.component.html',
  styleUrls: ['./program-stage-history.component.scss'],
})
export class ProgramStageHistoryComponent implements OnInit {
  programStage: IProgramStage | null = null;
  programStages?: IProgramStage[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected programStageService: ProgramStageService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IProgramStage): string => this.programStageService.getProgramStageIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programStage }) => {
      this.programStage = programStage;
      this.load();
    });
  }

  load(): void {
    this.programStageService
      .history(this.programStage?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.programStages = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(programStage: IProgramStage) {
    if (this.setRev.length < 2 && programStage.isSelected) {
      this.setRev.push(programStage.revisionNumber as number);
    } else if (!programStage.isSelected) {
      const index = this.setRev.indexOf(programStage.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      programStage.isSelected = false;
    }
  }
}
