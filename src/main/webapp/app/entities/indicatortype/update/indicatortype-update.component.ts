import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IndicatortypeFormService, IndicatortypeFormGroup } from './indicatortype-form.service';
import { IIndicatortype } from '../indicatortype.model';
import { IndicatortypeService } from '../service/indicatortype.service';

@Component({
  selector: 'jhi-indicatortype-update',
  templateUrl: './indicatortype-update.component.html',
})
export class IndicatortypeUpdateComponent implements OnInit {
  isSaving = false;
  indicatortype: IIndicatortype | null = null;

  editForm: IndicatortypeFormGroup = this.indicatortypeFormService.createIndicatortypeFormGroup();

  constructor(
    protected indicatortypeService: IndicatortypeService,
    protected indicatortypeFormService: IndicatortypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicatortype }) => {
      this.indicatortype = indicatortype;
      if (indicatortype) {
        this.updateForm(indicatortype);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicatortype = this.indicatortypeFormService.getIndicatortype(this.editForm);
    if (indicatortype.id !== null) {
      this.subscribeToSaveResponse(this.indicatortypeService.update(indicatortype));
    } else {
      this.subscribeToSaveResponse(this.indicatortypeService.create(indicatortype));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicatortype>>): void {
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

  protected updateForm(indicatortype: IIndicatortype): void {
    this.indicatortype = indicatortype;
    this.indicatortypeFormService.resetForm(this.editForm, indicatortype);
  }
}
