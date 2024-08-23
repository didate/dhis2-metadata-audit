import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';

@Component({
  selector: 'jhi-tracked-entity-attribute-detail',
  templateUrl: './tracked-entity-attribute-detail.component.html',
})
export class TrackedEntityAttributeDetailComponent implements OnInit {
  trackedEntityAttribute: ITrackedEntityAttribute | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trackedEntityAttribute }) => {
      this.trackedEntityAttribute = trackedEntityAttribute;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
