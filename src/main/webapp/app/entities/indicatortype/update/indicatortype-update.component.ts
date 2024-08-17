import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IIndicatortype } from '../indicatortype.model';
import { IndicatortypeService } from '../service/indicatortype.service';
import { IndicatortypeFormService, IndicatortypeFormGroup } from './indicatortype-form.service';

@Component({
  standalone: true,
  selector: 'jhi-indicatortype-update',
  templateUrl: './indicatortype-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IndicatortypeUpdateComponent implements OnInit {
  isSaving = false;
  indicatortype: IIndicatortype | null = null;

  protected indicatortypeService = inject(IndicatortypeService);
  protected indicatortypeFormService = inject(IndicatortypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IndicatortypeFormGroup = this.indicatortypeFormService.createIndicatortypeFormGroup();

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
