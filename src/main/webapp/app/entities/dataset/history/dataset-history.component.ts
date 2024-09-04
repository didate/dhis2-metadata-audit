import { Component, OnInit } from '@angular/core';
import { IDataset } from '../dataset.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { DatasetService, EntityArrayResponseType } from '../service/dataset.service';

@Component({
  selector: 'jhi-dataset-history',
  templateUrl: './dataset-history.component.html',
  styleUrls: ['./dataset-history.component.scss'],
})
export class DatasetHistoryComponent implements OnInit {
  dataset: IDataset | null = null;
  datasets?: IDataset[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected datasetService: DatasetService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IDataset): string => this.datasetService.getDatasetIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataset }) => {
      this.dataset = dataset;
      this.load();
    });
  }

  load(): void {
    this.datasetService
      .history(this.dataset?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.datasets = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(dataset: IDataset) {
    if (this.setRev.length < 2 && dataset.isSelected) {
      this.setRev.push(dataset.revisionNumber as number);
    } else if (!dataset.isSelected) {
      const index = this.setRev.indexOf(dataset.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      dataset.isSelected = false;
    }
  }
}
