import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OptionGroupFormService, OptionGroupFormGroup } from './option-group-form.service';
import { IOptionGroup } from '../option-group.model';
import { OptionGroupService } from '../service/option-group.service';

@Component({
  selector: 'jhi-option-group-update',
  templateUrl: './option-group-update.component.html',
})
export class OptionGroupUpdateComponent implements OnInit {
  isSaving = false;
  optionGroup: IOptionGroup | null = null;

  editForm: OptionGroupFormGroup = this.optionGroupFormService.createOptionGroupFormGroup();

  constructor(
    protected optionGroupService: OptionGroupService,
    protected optionGroupFormService: OptionGroupFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionGroup }) => {
      this.optionGroup = optionGroup;
      if (optionGroup) {
        this.updateForm(optionGroup);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const optionGroup = this.optionGroupFormService.getOptionGroup(this.editForm);
    if (optionGroup.id !== null) {
      this.subscribeToSaveResponse(this.optionGroupService.update(optionGroup));
    } else {
      this.subscribeToSaveResponse(this.optionGroupService.create(optionGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOptionGroup>>): void {
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

  protected updateForm(optionGroup: IOptionGroup): void {
    this.optionGroup = optionGroup;
    this.optionGroupFormService.resetForm(this.editForm, optionGroup);
  }
}
