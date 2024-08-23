import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OptionsetFormService, OptionsetFormGroup } from './optionset-form.service';
import { IOptionset } from '../optionset.model';
import { OptionsetService } from '../service/optionset.service';

@Component({
  selector: 'jhi-optionset-update',
  templateUrl: './optionset-update.component.html',
})
export class OptionsetUpdateComponent implements OnInit {
  isSaving = false;
  optionset: IOptionset | null = null;

  editForm: OptionsetFormGroup = this.optionsetFormService.createOptionsetFormGroup();

  constructor(
    protected optionsetService: OptionsetService,
    protected optionsetFormService: OptionsetFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

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
