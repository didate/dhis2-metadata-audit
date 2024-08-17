import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOptionset } from '../optionset.model';
import { OptionsetService } from '../service/optionset.service';
import { OptionsetFormService, OptionsetFormGroup } from './optionset-form.service';

@Component({
  standalone: true,
  selector: 'jhi-optionset-update',
  templateUrl: './optionset-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OptionsetUpdateComponent implements OnInit {
  isSaving = false;
  optionset: IOptionset | null = null;

  protected optionsetService = inject(OptionsetService);
  protected optionsetFormService = inject(OptionsetFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OptionsetFormGroup = this.optionsetFormService.createOptionsetFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionset }) => {
      this.optionset = optionset;
      if (optionset) {
        this.updateForm(optionset);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optionset = this.optionsetFormService.getOptionset(this.editForm);
    if (optionset.id !== null) {
      this.subscribeToSaveResponse(this.optionsetService.update(optionset));
    } else {
      this.subscribeToSaveResponse(this.optionsetService.create(optionset));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionset>>): void {
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

  protected updateForm(optionset: IOptionset): void {
    this.optionset = optionset;
    this.optionsetFormService.resetForm(this.editForm, optionset);
  }
}
