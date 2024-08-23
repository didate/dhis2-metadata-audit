import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { OrganisationUnitFormService, OrganisationUnitFormGroup } from './organisation-unit-form.service';
import { IOrganisationUnit } from '../organisation-unit.model';
import { OrganisationUnitService } from '../service/organisation-unit.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-organisation-unit-update',
  templateUrl: './organisation-unit-update.component.html',
})
export class OrganisationUnitUpdateComponent implements OnInit {
  isSaving = false;
  organisationUnit: IOrganisationUnit | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  dHISUsersSharedCollection: IDHISUser[] = [];

  editForm: OrganisationUnitFormGroup = this.organisationUnitFormService.createOrganisationUnitFormGroup();

  constructor(
    protected organisationUnitService: OrganisationUnitService,
    protected organisationUnitFormService: OrganisationUnitFormService,
    protected dHISUserService: DHISUserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisationUnit }) => {
      this.organisationUnit = organisationUnit;
      if (organisationUnit) {
        this.updateForm(organisationUnit);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisationUnit = this.organisationUnitFormService.getOrganisationUnit(this.editForm);
    if (organisationUnit.id !== null) {
      this.subscribeToSaveResponse(this.organisationUnitService.update(organisationUnit));
    } else {
      this.subscribeToSaveResponse(this.organisationUnitService.create(organisationUnit));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisationUnit>>): void {
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

  protected updateForm(organisationUnit: IOrganisationUnit): void {
    this.organisationUnit = organisationUnit;
    this.organisationUnitFormService.resetForm(this.editForm, organisationUnit);

    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      organisationUnit.createdBy,
      organisationUnit.lastUpdatedBy
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.organisationUnit?.createdBy,
            this.organisationUnit?.lastUpdatedBy
          )
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));
  }
}
