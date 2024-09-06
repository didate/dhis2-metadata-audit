import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramStage } from '../program-stage.model';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';
import { ProgramStageService, EntityArrayResponseType } from '../service/program-stage.service';

@Component({
  selector: 'jhi-program-stage-detail',
  templateUrl: './program-stage-detail.component.html',
})
export class ProgramStageDetailComponent implements OnInit {
  programStage: IProgramStage | null = null;

  programStageRev1: IProgramStage | null = null;
  programStageRev2: IProgramStage | null = null;

  programStages?: IProgramStage[];
  isLoading = false;

  rev1: number = 0;
  rev2: number = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected programStageService: ProgramStageService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ programStage }) => {
        this.programStage = programStage;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    this.programStageService
      .compare(this.programStage?.id!, rev1, rev2)
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
    this.programStages = dataFromBody;
    this.programStageRev1 = this.programStages[0];
    this.programStageRev2 = this.programStages[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IProgramStage[] | null): IProgramStage[] {
    return data ?? [];
  }
}
