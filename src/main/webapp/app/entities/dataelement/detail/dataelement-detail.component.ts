import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataelement } from '../dataelement.model';

@Component({
  selector: 'jhi-dataelement-detail',
  templateUrl: './dataelement-detail.component.html',
})
export class DataelementDetailComponent implements OnInit {
  dataelement: IDataelement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataelement }) => {
      this.dataelement = dataelement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
