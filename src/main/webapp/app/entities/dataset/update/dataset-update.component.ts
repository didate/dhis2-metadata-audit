import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { DatasetService } from '../service/dataset.service';
import { IDataset } from '../dataset.model';
import { DatasetFormService, DatasetFormGroup } from './dataset-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dataset-update',
  templateUrl: './dataset-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DatasetUpdateComponent implements OnInit {
  isSaving = false;
  dataset: IDataset | null = null;

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];

  protected datasetService = inject(DatasetService);
  protected datasetFormService = inject(DatasetFormService);
  protected projectService = inject(ProjectService);
  protected dHISUserService = inject(DHISUserService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DatasetFormGroup = this.datasetFormService.createDatasetFormGroup();

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataset }) => {
      this.dataset = dataset;
      if (dataset) {
        this.updateForm(dataset);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataset = this.datasetFormService.getDataset(this.editForm);
    if (dataset.id !== null) {
      this.subscribeToSaveResponse(this.datasetService.update(dataset));
    } else {
      this.subscribeToSaveResponse(this.datasetService.create(dataset));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataset>>): void {
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

  protected updateForm(dataset: IDataset): void {
    this.dataset = dataset;
    this.datasetFormService.resetForm(this.editForm, dataset);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      dataset.project,
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      dataset.createdBy,
      dataset.lastUpdatedBy,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.dataset?.project)))
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(dHISUsers, this.dataset?.createdBy, this.dataset?.lastUpdatedBy),
        ),
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));
  }
}
