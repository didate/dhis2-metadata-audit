import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DHISUserFormService, DHISUserFormGroup } from './dhis-user-form.service';
import { IDHISUser } from '../dhis-user.model';
import { DHISUserService } from '../service/dhis-user.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-dhis-user-update',
  templateUrl: './dhis-user-update.component.html',
})
export class DHISUserUpdateComponent implements OnInit {
  isSaving = false;
  dHISUser: IDHISUser | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  editForm: DHISUserFormGroup = this.dHISUserFormService.createDHISUserFormGroup();

  constructor(
    protected dHISUserService: DHISUserService,
    protected dHISUserFormService: DHISUserFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

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
