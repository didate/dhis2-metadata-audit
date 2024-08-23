import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOptionGroup } from '../option-group.model';

@Component({
  selector: 'jhi-option-group-detail',
  templateUrl: './option-group-detail.component.html',
})
export class OptionGroupDetailComponent implements OnInit {
  optionGroup: IOptionGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionGroup }) => {
      this.optionGroup = optionGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
