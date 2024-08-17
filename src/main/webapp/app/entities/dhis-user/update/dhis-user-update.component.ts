import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDHISUser } from '../dhis-user.model';
import { DHISUserService } from '../service/dhis-user.service';
import { DHISUserFormService, DHISUserFormGroup } from './dhis-user-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dhis-user-update',
  templateUrl: './dhis-user-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DHISUserUpdateComponent implements OnInit {
  isSaving = false;
  dHISUser: IDHISUser | null = null;

  protected dHISUserService = inject(DHISUserService);
  protected dHISUserFormService = inject(DHISUserFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DHISUserFormGroup = this.dHISUserFormService.createDHISUserFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dHISUser }) => {
      this.dHISUser = dHISUser;
      if (dHISUser) {
        this.updateForm(dHISUser);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dHISUser = this.dHISUserFormService.getDHISUser(this.editForm);
    if (dHISUser.id !== null) {
      this.subscribeToSaveResponse(this.dHISUserService.update(dHISUser));
    } else {
      this.subscribeToSaveResponse(this.dHISUserService.create(dHISUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDHISUser>>): void {
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

  protected updateForm(dHISUser: IDHISUser): void {
    this.dHISUser = dHISUser;
    this.dHISUserFormService.resetForm(this.editForm, dHISUser);
  }
}
