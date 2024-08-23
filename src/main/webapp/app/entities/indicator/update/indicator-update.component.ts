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
import { IIndicatortype } from 'app/entities/indicatortype/indicatortype.model';
import { IndicatortypeService } from 'app/entities/indicatortype/service/indicatortype.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';
import { IndicatorService } from '../service/indicator.service';
import { IIndicator } from '../indicator.model';
import { IndicatorFormService, IndicatorFormGroup } from './indicator-form.service';

@Component({
  standalone: true,
  selector: 'jhi-indicator-update',
  templateUrl: './indicator-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class IndicatorUpdateComponent implements OnInit {
  isSaving = false;
  indicator: IIndicator | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  indicatortypesSharedCollection: IIndicatortype[] = [];

  protected indicatorService = inject(IndicatorService);
  protected indicatorFormService = inject(IndicatorFormService);
  protected projectService = inject(ProjectService);
  protected dHISUserService = inject(DHISUserService);
  protected indicatortypeService = inject(IndicatortypeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: IndicatorFormGroup = this.indicatorFormService.createIndicatorFormGroup();

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareIndicatortype = (o1: IIndicatortype | null, o2: IIndicatortype | null): boolean =>
    this.indicatortypeService.compareIndicatortype(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicator }) => {
      this.indicator = indicator;
      if (indicator) {
        this.updateForm(indicator);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicator = this.indicatorFormService.getIndicator(this.editForm);
    if (indicator.id !== null) {
      this.subscribeToSaveResponse(this.indicatorService.update(indicator));
    } else {
      this.subscribeToSaveResponse(this.indicatorService.create(indicator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicator>>): void {
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

  protected updateForm(indicator: IIndicator): void {
    this.indicator = indicator;
    this.indicatorFormService.resetForm(this.editForm, indicator);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      indicator.project,
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      indicator.createdBy,
      indicator.lastUpdatedBy,
    );
    this.indicatortypesSharedCollection = this.indicatortypeService.addIndicatortypeToCollectionIfMissing<IIndicatortype>(
      this.indicatortypesSharedCollection,
      indicator.indicatorType,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.indicator?.project)))
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.indicator?.createdBy,
            this.indicator?.lastUpdatedBy,
          ),
        ),
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.indicatortypeService
      .query()
      .pipe(map((res: HttpResponse<IIndicatortype[]>) => res.body ?? []))
      .pipe(
        map((indicatortypes: IIndicatortype[]) =>
          this.indicatortypeService.addIndicatortypeToCollectionIfMissing<IIndicatortype>(indicatortypes, this.indicator?.indicatorType),
        ),
      )
      .subscribe((indicatortypes: IIndicatortype[]) => (this.indicatortypesSharedCollection = indicatortypes));
  }
}
