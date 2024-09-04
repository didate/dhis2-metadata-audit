import { Component, OnInit } from '@angular/core';
import { IProgramIndicator } from '../program-indicator.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { EntityArrayResponseType, ProgramIndicatorService } from '../service/program-indicator.service';

@Component({
  selector: 'jhi-program-indicator-history',
  templateUrl: './program-indicator-history.component.html',
  styleUrls: ['./program-indicator-history.component.scss'],
})
export class ProgramIndicatorHistoryComponent implements OnInit {
  programIndicator: IProgramIndicator | null = null;
  programIndicators?: IProgramIndicator[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected programIndicatorService: ProgramIndicatorService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IProgramIndicator): string => this.programIndicatorService.getProgramIndicatorIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programIndicator }) => {
      this.programIndicator = programIndicator;
      this.load();
    });
  }

  load(): void {
    this.programIndicatorService
      .history(this.programIndicator?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.programIndicators = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(programIndicator: IProgramIndicator) {
    if (this.setRev.length < 2 && programIndicator.isSelected) {
      this.setRev.push(programIndicator.revisionNumber as number);
    } else if (!programIndicator.isSelected) {
      const index = this.setRev.indexOf(programIndicator.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      programIndicator.isSelected = false;
    }
  }
}
