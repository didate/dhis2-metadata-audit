import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramIndicator } from '../program-indicator.model';

@Component({
  selector: 'jhi-program-indicator-detail',
  templateUrl: './program-indicator-detail.component.html',
})
export class ProgramIndicatorDetailComponent implements OnInit {
  programIndicator: IProgramIndicator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programIndicator }) => {
      this.programIndicator = programIndicator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
