import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicatortype } from '../indicatortype.model';

@Component({
  selector: 'jhi-indicatortype-detail',
  templateUrl: './indicatortype-detail.component.html',
})
export class IndicatortypeDetailComponent implements OnInit {
  indicatortype: IIndicatortype | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicatortype }) => {
      this.indicatortype = indicatortype;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
