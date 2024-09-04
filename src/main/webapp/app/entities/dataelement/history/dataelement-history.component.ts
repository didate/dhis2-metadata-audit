import { Component, OnInit } from '@angular/core';
import { IDataelement } from '../dataelement.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { DataelementService, EntityArrayResponseType } from '../service/dataelement.service';

@Component({
  selector: 'jhi-dataelement-history',
  templateUrl: './dataelement-history.component.html',
  styleUrls: ['./dataelement-history.component.scss'],
})
export class DataelementHistoryComponent implements OnInit {
  dataelement: IDataelement | null = null;
  dataelements?: IDataelement[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected dataelementService: DataelementService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IDataelement): string => this.dataelementService.getDataelementIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataelement }) => {
      this.dataelement = dataelement;
      this.load();
    });
  }

  load(): void {
    this.dataelementService
      .history(this.dataelement?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.dataelements = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(dataelement: IDataelement) {
    if (this.setRev.length < 2 && dataelement.isSelected) {
      this.setRev.push(dataelement.revisionNumber as number);
    } else if (!dataelement.isSelected) {
      const index = this.setRev.indexOf(dataelement.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      dataelement.isSelected = false;
    }
  }
}
