import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOptionGroup } from '../option-group.model';
import { OptionGroupService } from '../service/option-group.service';
import { OptionGroupFormService, OptionGroupFormGroup } from './option-group-form.service';

@Component({
  standalone: true,
  selector: 'jhi-option-group-update',
  templateUrl: './option-group-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OptionGroupUpdateComponent implements OnInit {
  isSaving = false;
  optionGroup: IOptionGroup | null = null;

  protected optionGroupService = inject(OptionGroupService);
  protected optionGroupFormService = inject(OptionGroupFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OptionGroupFormGroup = this.optionGroupFormService.createOptionGroupFormGroup();

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
    this.subscribeToSaveResponse(this.optionGroupService.create(optionGroup));
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
