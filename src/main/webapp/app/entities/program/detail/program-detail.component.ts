import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgram } from '../program.model';
import { EntityArrayResponseType, ProgramService } from '../service/program.service';
import { tap } from 'rxjs';
import { DiffService } from 'app/entities/diff.service';

@Component({
  selector: 'jhi-program-detail',
  templateUrl: './program-detail.component.html',
})
export class ProgramDetailComponent implements OnInit {
  program: IProgram | null = null;
  programRev1: IProgram | null = null;
  programRev2: IProgram | null = null;

  programs?: IProgram[];
  isLoading = false;

  rev1: number = 0;
  rev2: number = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected programService: ProgramService, private diffService: DiffService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ program }) => {
        this.program = program;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    this.programService
      .compare(this.program?.id!, rev1, rev2)
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
    this.programs = dataFromBody;
    this.programRev1 = this.programs[0];
    this.programRev2 = this.programs[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IProgram[] | null): IProgram[] {
    return data ?? [];
  }
}
