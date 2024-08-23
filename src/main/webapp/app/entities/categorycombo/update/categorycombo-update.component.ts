import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategorycomboFormService, CategorycomboFormGroup } from './categorycombo-form.service';
import { ICategorycombo } from '../categorycombo.model';
import { CategorycomboService } from '../service/categorycombo.service';

@Component({
  selector: 'jhi-categorycombo-update',
  templateUrl: './categorycombo-update.component.html',
})
export class CategorycomboUpdateComponent implements OnInit {
  isSaving = false;
  categorycombo: ICategorycombo | null = null;

  editForm: CategorycomboFormGroup = this.categorycomboFormService.createCategorycomboFormGroup();

  constructor(
    protected categorycomboService: CategorycomboService,
    protected categorycomboFormService: CategorycomboFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorycombo }) => {
      this.categorycombo = categorycombo;
      if (categorycombo) {
        this.updateForm(categorycombo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorycombo = this.categorycomboFormService.getCategorycombo(this.editForm);
    if (categorycombo.id !== null) {
      this.subscribeToSaveResponse(this.categorycomboService.update(categorycombo));
    } else {
      this.subscribeToSaveResponse(this.categorycomboService.create(categorycombo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorycombo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categorycombo: ICategorycombo): void {
    this.categorycombo = categorycombo;
    this.categorycomboFormService.resetForm(this.editForm, categorycombo);
  }
}
