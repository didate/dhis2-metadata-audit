import { Component, OnInit } from '@angular/core';
import { IIndicator } from '../indicator.model';
import { EntityArrayResponseType, IndicatorService } from '../service/indicator.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-indicator-history',
  templateUrl: './indicator-history.component.html',
  styleUrls: ['./indicator-history.component.scss'],
})
export class IndicatorHistoryComponent implements OnInit {
  indicator: IIndicator | null = null;
  indicators?: IIndicator[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected indicatorService: IndicatorService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IIndicator): string => this.indicatorService.getIndicatorIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicator }) => {
      this.indicator = indicator;
      this.load();
    });
  }

  load(): void {
    this.indicatorService
      .history(this.indicator?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.indicators = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(indicator: IIndicator) {
    if (this.setRev.length < 2 && indicator.isSelected) {
      this.setRev.push(indicator.revisionNumber as number);
    } else if (!indicator.isSelected) {
      const index = this.setRev.indexOf(indicator.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      indicator.isSelected = false;
    }
  }
}
