import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicator } from '../indicator.model';

@Component({
  selector: 'jhi-indicator-detail',
  templateUrl: './indicator-detail.component.html',
})
export class IndicatorDetailComponent implements OnInit {
  indicator: IIndicator | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicator }) => {
      this.indicator = indicator;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
