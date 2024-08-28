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

  predicate = 'id';
  ascending = true;

  itemsPerPage = ITEMS_PER_PAGE;
  totalItems = 0;
  page = 1;

  setRev: Set<number | null | undefined> = new Set<number | null | undefined>();

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

    this.load();
  }

  load(): void {
    this.programService
      .history(this.program?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.page, this.predicate, this.ascending);
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(program: IProgram) {
    if (this.setRev.size < 2 && program.isSelected) {
      this.setRev.add(program.revisionNumber);
    } else if (!program.isSelected) {
      this.setRev.delete(program.revisionNumber);
    } else {
      program.isSelected = false;
    }
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    this.fillComponentAttributesFromResponseHeader(response.headers);
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.programs = dataFromBody;
  }

  protected fillComponentAttributesFromResponseBody(data: IProgram[] | null): IProgram[] {
    return data ?? [];
  }

  protected fillComponentAttributesFromResponseHeader(headers: HttpHeaders): void {
    this.totalItems = Number(headers.get(TOTAL_COUNT_RESPONSE_HEADER));
  }

  protected handleNavigation(page = this.page, predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      page,
      size: this.itemsPerPage,
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}
