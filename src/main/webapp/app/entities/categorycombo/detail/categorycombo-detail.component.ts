import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorycombo } from '../categorycombo.model';
import { CategorycomboService, EntityArrayResponseType } from '../service/categorycombo.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-categorycombo-detail',
  templateUrl: './categorycombo-detail.component.html',
})
export class CategorycomboDetailComponent implements OnInit {
  categorycombo: ICategorycombo | null = null;

  categorycomboRev1: ICategorycombo | null = null;
  categorycomboRev2: ICategorycombo | null = null;

  categorycombos?: ICategorycombo[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected categorycomboService: CategorycomboService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ categorycombo }) => {
        this.categorycombo = categorycombo;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.categorycombo) {
      this.categorycomboService
        .compare(this.categorycombo.id, rev1, rev2)
        .pipe(tap(() => (this.isLoading = false)))
        .subscribe({
          next: (res: EntityArrayResponseType) => {
            this.onResponseSuccess(res);
          },
        });
    }
  }

  showDiff(text1: any, text2: any): any {
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
    this.categorycombos = dataFromBody;
    this.categorycomboRev1 = this.categorycombos[0];
    this.categorycomboRev2 = this.categorycombos[1];
  }

  protected fillComponentAttributesFromResponseBody(data: ICategorycombo[] | null): ICategorycombo[] {
    return data ?? [];
  }
}
