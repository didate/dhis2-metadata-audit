import { Component, OnInit } from '@angular/core';
import { IOptionset } from '../optionset.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { OptionsetService, EntityArrayResponseType } from '../service/optionset.service';

@Component({
  selector: 'jhi-optionset-history',
  templateUrl: './optionset-history.component.html',
  styleUrls: ['./optionset-history.component.scss'],
})
export class OptionsetHistoryComponent implements OnInit {
  optionset: IOptionset | null = null;
  optionsets?: IOptionset[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected optionsetService: OptionsetService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IOptionset): string => this.optionsetService.getOptionsetIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionset }) => {
      this.optionset = optionset;
      this.load();
    });
  }

  load(): void {
    this.optionsetService
      .history(this.optionset?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.optionsets = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(optionset: IOptionset) {
    if (this.setRev.length < 2 && optionset.isSelected) {
      this.setRev.push(optionset.revisionNumber as number);
    } else if (!optionset.isSelected) {
      const index = this.setRev.indexOf(optionset.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      optionset.isSelected = false;
    }
  }
}
