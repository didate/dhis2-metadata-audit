import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PersonNotifierFormService, PersonNotifierFormGroup } from './person-notifier-form.service';
import { IPersonNotifier } from '../person-notifier.model';
import { PersonNotifierService } from '../service/person-notifier.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';

@Component({
  selector: 'jhi-person-notifier-update',
  templateUrl: './person-notifier-update.component.html',
})
export class PersonNotifierUpdateComponent implements OnInit {
  isSaving = false;
  personNotifier: IPersonNotifier | null = null;

  projectsSharedCollection: IProject[] = [];

  editForm: PersonNotifierFormGroup = this.personNotifierFormService.createPersonNotifierFormGroup();

  constructor(
    protected personNotifierService: PersonNotifierService,
    protected personNotifierFormService: PersonNotifierFormService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personNotifier }) => {
      this.personNotifier = personNotifier;
      if (personNotifier) {
        this.updateForm(personNotifier);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const personNotifier = this.personNotifierFormService.getPersonNotifier(this.editForm);
    if (personNotifier.id !== null) {
      this.subscribeToSaveResponse(this.personNotifierService.update(personNotifier));
    } else {
      this.subscribeToSaveResponse(this.personNotifierService.create(personNotifier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersonNotifier>>): void {
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

  protected updateForm(personNotifier: IPersonNotifier): void {
    this.personNotifier = personNotifier;
    this.personNotifierFormService.resetForm(this.editForm, personNotifier);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      personNotifier.project
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.personNotifier?.project))
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }
}
