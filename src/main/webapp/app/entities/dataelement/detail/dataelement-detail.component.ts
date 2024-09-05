import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataelement } from '../dataelement.model';
import { DataelementService, EntityArrayResponseType } from '../service/dataelement.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-dataelement-detail',
  templateUrl: './dataelement-detail.component.html',
})
export class DataelementDetailComponent implements OnInit {
  dataelement: IDataelement | null = null;

  dataelementRev1: IDataelement | null = null;
  dataelementRev2: IDataelement | null = null;

  dataelements?: IDataelement[];
  isLoading = false;

  rev1: number = 0;
  rev2: number = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected dataelementService: DataelementService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ dataelement }) => {
        this.dataelement = dataelement;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    this.dataelementService
      .compare(this.dataelement?.id!, rev1, rev2)
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
    this.dataelements = dataFromBody;
    this.dataelementRev1 = this.dataelements[0];
    this.dataelementRev2 = this.dataelements[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IDataelement[] | null): IDataelement[] {
    return data ?? [];
  }
}
