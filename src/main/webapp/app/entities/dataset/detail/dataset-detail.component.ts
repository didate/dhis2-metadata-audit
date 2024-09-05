import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataset } from '../dataset.model';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';
import { DatasetService, EntityArrayResponseType } from '../service/dataset.service';

@Component({
  selector: 'jhi-dataset-detail',
  templateUrl: './dataset-detail.component.html',
})
export class DatasetDetailComponent implements OnInit {
  dataset: IDataset | null = null;
  datasetRev1: IDataset | null = null;
  datasetRev2: IDataset | null = null;

  datasets?: IDataset[];
  isLoading = false;

  rev1: number = 0;
  rev2: number = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected datasetService: DatasetService, private diffService: DiffService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ dataset }) => {
        this.dataset = dataset;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    this.datasetService
      .compare(this.dataset?.id!, rev1, rev2)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  showDiff(text1: any, text2: any) {
    if (text1) {
      return this.diffService.generateDiff(text1 as string, text2 as string);
    }
    return text1;
  }

  previousState(): void {
    window.history.back();
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.datasets = dataFromBody;
    this.datasetRev1 = this.datasets[0];
    this.datasetRev2 = this.datasets[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IDataset[] | null): IDataset[] {
    return data ?? [];
  }
}
