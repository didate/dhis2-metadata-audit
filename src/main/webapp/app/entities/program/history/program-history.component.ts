import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap, Data } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SORT, DEFAULT_SORT_DATA, ASC, DESC } from 'app/config/navigation.constants';
import { ITEMS_PER_PAGE, PAGE_HEADER, TOTAL_COUNT_RESPONSE_HEADER } from 'app/config/pagination.constants';
import { Observable, combineLatest, tap, switchMap } from 'rxjs';
import { IProgram } from '../program.model';
import { ProgramService, EntityArrayResponseType } from '../service/program.service';

@Component({
  selector: 'jhi-program-history',
  templateUrl: './program-history.component.html',
  styleUrls: ['./program-history.component.scss'],
})
export class ProgramHistoryComponent implements OnInit {
  program: IProgram | null = null;
  programs?: IProgram[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected programService: ProgramService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IProgram): string => this.programService.getProgramIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ program }) => {
      this.program = program;
      this.load();
    });
  }

  load(): void {
    this.programService
      .history(this.program?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.programs = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(program: IProgram) {
    if (this.setRev.length < 2 && program.isSelected) {
      this.setRev.push(program.revisionNumber as number);
    } else if (!program.isSelected) {
      const index = this.setRev.indexOf(program.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      program.isSelected = false;
    }
  }
}
