import { Component, OnInit } from '@angular/core';
import { IIndicatortype } from '../indicatortype.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { EntityArrayResponseType, IndicatortypeService } from '../service/indicatortype.service';

@Component({
  selector: 'jhi-indicatortype-history',
  templateUrl: './indicatortype-history.component.html',
  styleUrls: ['./indicatortype-history.component.scss'],
})
export class IndicatortypeHistoryComponent implements OnInit {
  indicatortype: IIndicatortype | null = null;
  indicatortypes?: IIndicatortype[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected indicatortypeService: IndicatortypeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IIndicatortype): string => this.indicatortypeService.getIndicatortypeIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicatortype }) => {
      this.indicatortype = indicatortype;
      this.load();
    });
  }

  load(): void {
    this.indicatortypeService
      .history(this.indicatortype?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.indicatortypes = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(indicatortype: IIndicatortype) {
    if (this.setRev.length < 2 && indicatortype.isSelected) {
      this.setRev.push(indicatortype.revisionNumber as number);
    } else if (!indicatortype.isSelected) {
      const index = this.setRev.indexOf(indicatortype.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      indicatortype.isSelected = false;
    }
  }
}
