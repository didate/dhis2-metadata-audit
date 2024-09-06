import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { EntityArrayResponseType, TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-tracked-entity-attribute-detail',
  templateUrl: './tracked-entity-attribute-detail.component.html',
})
export class TrackedEntityAttributeDetailComponent implements OnInit {
  trackedEntityAttribute: ITrackedEntityAttribute | null = null;
  trackedEntityAttributeRev1: ITrackedEntityAttribute | null = null;
  trackedEntityAttributeRev2: ITrackedEntityAttribute | null = null;

  trackedEntityAttributes?: ITrackedEntityAttribute[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected trackedEntityAttributeService: TrackedEntityAttributeService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ trackedEntityAttribute }) => {
        this.trackedEntityAttribute = trackedEntityAttribute;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.trackedEntityAttribute) {
      this.trackedEntityAttributeService
        .compare(this.trackedEntityAttribute.id, rev1, rev2)
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
    this.trackedEntityAttributes = dataFromBody;
    this.trackedEntityAttributeRev1 = this.trackedEntityAttributes[0];
    this.trackedEntityAttributeRev2 = this.trackedEntityAttributes[1];
  }

  protected fillComponentAttributesFromResponseBody(data: ITrackedEntityAttribute[] | null): ITrackedEntityAttribute[] {
    return data ?? [];
  }
}
