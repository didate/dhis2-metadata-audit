import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IDataset } from 'app/entities/dataset/dataset.model';
import { DatasetService } from 'app/entities/dataset/service/dataset.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';
import { OrganisationUnitService } from '../service/organisation-unit.service';
import { IOrganisationUnit } from '../organisation-unit.model';
import { OrganisationUnitFormService, OrganisationUnitFormGroup } from './organisation-unit-form.service';

@Component({
  standalone: true,
  selector: 'jhi-organisation-unit-update',
  templateUrl: './organisation-unit-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OrganisationUnitUpdateComponent implements OnInit {
  isSaving = false;
  organisationUnit: IOrganisationUnit | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  dHISUsersSharedCollection: IDHISUser[] = [];
  programsSharedCollection: IProgram[] = [];
  datasetsSharedCollection: IDataset[] = [];

  protected organisationUnitService = inject(OrganisationUnitService);
  protected organisationUnitFormService = inject(OrganisationUnitFormService);
  protected dHISUserService = inject(DHISUserService);
  protected programService = inject(ProgramService);
  protected datasetService = inject(DatasetService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OrganisationUnitFormGroup = this.organisationUnitFormService.createOrganisationUnitFormGroup();

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareProgram = (o1: IProgram | null, o2: IProgram | null): boolean => this.programService.compareProgram(o1, o2);

  compareDataset = (o1: IDataset | null, o2: IDataset | null): boolean => this.datasetService.compareDataset(o1, o2);

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
      organisationUnit.lastUpdatedBy,
    );
    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing<IProgram>(
      this.programsSharedCollection,
      ...(organisationUnit.programs ?? []),
    );
    this.datasetsSharedCollection = this.datasetService.addDatasetToCollectionIfMissing<IDataset>(
      this.datasetsSharedCollection,
      ...(organisationUnit.datasets ?? []),
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
            this.organisationUnit?.lastUpdatedBy,
          ),
        ),
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) =>
          this.programService.addProgramToCollectionIfMissing<IProgram>(programs, ...(this.organisationUnit?.programs ?? [])),
        ),
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));

    this.datasetService
      .query()
      .pipe(map((res: HttpResponse<IDataset[]>) => res.body ?? []))
      .pipe(
        map((datasets: IDataset[]) =>
          this.datasetService.addDatasetToCollectionIfMissing<IDataset>(datasets, ...(this.organisationUnit?.datasets ?? [])),
        ),
      )
      .subscribe((datasets: IDataset[]) => (this.datasetsSharedCollection = datasets));
  }
}
