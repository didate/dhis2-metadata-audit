import { Component, OnInit } from '@angular/core';
import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { TrackedEntityAttributeService, EntityArrayResponseType } from '../service/tracked-entity-attribute.service';

@Component({
  selector: 'jhi-tracked-entity-attribute-history',
  templateUrl: './tracked-entity-attribute-history.component.html',
  styleUrls: ['./tracked-entity-attribute-history.component.scss'],
})
export class TrackedEntityAttributeHistoryComponent implements OnInit {
  trackedEntityAttribute: ITrackedEntityAttribute | null = null;
  trackedEntityAttributes?: ITrackedEntityAttribute[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected trackedEntityAttributeService: TrackedEntityAttributeService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: ITrackedEntityAttribute): string =>
    this.trackedEntityAttributeService.getTrackedEntityAttributeIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trackedEntityAttribute }) => {
      this.trackedEntityAttribute = trackedEntityAttribute;
      this.load();
    });
  }

  load(): void {
    this.trackedEntityAttributeService
      .history(this.trackedEntityAttribute?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.trackedEntityAttributes = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(trackedEntityAttribute: ITrackedEntityAttribute) {
    if (this.setRev.length < 2 && trackedEntityAttribute.isSelected) {
      this.setRev.push(trackedEntityAttribute.revisionNumber as number);
    } else if (!trackedEntityAttribute.isSelected) {
      const index = this.setRev.indexOf(trackedEntityAttribute.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      trackedEntityAttribute.isSelected = false;
    }
  }
}
