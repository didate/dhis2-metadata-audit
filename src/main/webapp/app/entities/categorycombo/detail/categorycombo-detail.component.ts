import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorycombo } from '../categorycombo.model';

@Component({
  selector: 'jhi-categorycombo-detail',
  templateUrl: './categorycombo-detail.component.html',
})
export class CategorycomboDetailComponent implements OnInit {
  categorycombo: ICategorycombo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorycombo }) => {
      this.categorycombo = categorycombo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
