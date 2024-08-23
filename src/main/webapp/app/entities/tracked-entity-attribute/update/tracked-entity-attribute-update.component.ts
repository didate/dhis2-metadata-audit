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
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { OptionsetService } from 'app/entities/optionset/service/optionset.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';
import { TrackedEntityAttributeService } from '../service/tracked-entity-attribute.service';
import { ITrackedEntityAttribute } from '../tracked-entity-attribute.model';
import { TrackedEntityAttributeFormService, TrackedEntityAttributeFormGroup } from './tracked-entity-attribute-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tracked-entity-attribute-update',
  templateUrl: './tracked-entity-attribute-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TrackedEntityAttributeUpdateComponent implements OnInit {
  isSaving = false;
  trackedEntityAttribute: ITrackedEntityAttribute | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  optionsetsSharedCollection: IOptionset[] = [];

  protected trackedEntityAttributeService = inject(TrackedEntityAttributeService);
  protected trackedEntityAttributeFormService = inject(TrackedEntityAttributeFormService);
  protected projectService = inject(ProjectService);
  protected dHISUserService = inject(DHISUserService);
  protected optionsetService = inject(OptionsetService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TrackedEntityAttributeFormGroup = this.trackedEntityAttributeFormService.createTrackedEntityAttributeFormGroup();

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareOptionset = (o1: IOptionset | null, o2: IOptionset | null): boolean => this.optionsetService.compareOptionset(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trackedEntityAttribute }) => {
      this.trackedEntityAttribute = trackedEntityAttribute;
      if (trackedEntityAttribute) {
        this.updateForm(trackedEntityAttribute);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trackedEntityAttribute = this.trackedEntityAttributeFormService.getTrackedEntityAttribute(this.editForm);
    if (trackedEntityAttribute.id !== null) {
      this.subscribeToSaveResponse(this.trackedEntityAttributeService.update(trackedEntityAttribute));
    } else {
      this.subscribeToSaveResponse(this.trackedEntityAttributeService.create(trackedEntityAttribute));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrackedEntityAttribute>>): void {
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

  protected updateForm(trackedEntityAttribute: ITrackedEntityAttribute): void {
    this.trackedEntityAttribute = trackedEntityAttribute;
    this.trackedEntityAttributeFormService.resetForm(this.editForm, trackedEntityAttribute);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      trackedEntityAttribute.project,
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      trackedEntityAttribute.createdBy,
      trackedEntityAttribute.lastUpdatedBy,
    );
    this.optionsetsSharedCollection = this.optionsetService.addOptionsetToCollectionIfMissing<IOptionset>(
      this.optionsetsSharedCollection,
      trackedEntityAttribute.optionSet,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.trackedEntityAttribute?.project),
        ),
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.trackedEntityAttribute?.createdBy,
            this.trackedEntityAttribute?.lastUpdatedBy,
          ),
        ),
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.optionsetService
      .query()
      .pipe(map((res: HttpResponse<IOptionset[]>) => res.body ?? []))
      .pipe(
        map((optionsets: IOptionset[]) =>
          this.optionsetService.addOptionsetToCollectionIfMissing<IOptionset>(optionsets, this.trackedEntityAttribute?.optionSet),
        ),
      )
      .subscribe((optionsets: IOptionset[]) => (this.optionsetsSharedCollection = optionsets));
  }
}
