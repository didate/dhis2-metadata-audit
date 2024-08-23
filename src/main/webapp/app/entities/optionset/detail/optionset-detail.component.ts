import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionset } from '../optionset.model';

@Component({
  selector: 'jhi-optionset-detail',
  templateUrl: './optionset-detail.component.html',
})
export class OptionsetDetailComponent implements OnInit {
  optionset: IOptionset | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionset }) => {
      this.optionset = optionset;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
