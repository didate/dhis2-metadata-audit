import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataset } from '../dataset.model';

@Component({
  selector: 'jhi-dataset-detail',
  templateUrl: './dataset-detail.component.html',
})
export class DatasetDetailComponent implements OnInit {
  dataset: IDataset | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataset }) => {
      this.dataset = dataset;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
