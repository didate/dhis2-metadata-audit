import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProgramRuleFormService, ProgramRuleFormGroup } from './program-rule-form.service';
import { IProgramRule } from '../program-rule.model';
import { ProgramRuleService } from '../service/program-rule.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-program-rule-update',
  templateUrl: './program-rule-update.component.html',
})
export class ProgramRuleUpdateComponent implements OnInit {
  isSaving = false;
  programRule: IProgramRule | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  programsSharedCollection: IProgram[] = [];

  editForm: ProgramRuleFormGroup = this.programRuleFormService.createProgramRuleFormGroup();

  constructor(
    protected programRuleService: ProgramRuleService,
    protected programRuleFormService: ProgramRuleFormService,
    protected projectService: ProjectService,
    protected dHISUserService: DHISUserService,
    protected programService: ProgramService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareProgram = (o1: IProgram | null, o2: IProgram | null): boolean => this.programService.compareProgram(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRule }) => {
      this.programRule = programRule;
      if (programRule) {
        this.updateForm(programRule);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programRule = this.programRuleFormService.getProgramRule(this.editForm);
    if (programRule.id !== null) {
      this.subscribeToSaveResponse(this.programRuleService.update(programRule));
    } else {
      this.subscribeToSaveResponse(this.programRuleService.create(programRule));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramRule>>): void {
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

  protected updateForm(programRule: IProgramRule): void {
    this.programRule = programRule;
    this.programRuleFormService.resetForm(this.editForm, programRule);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      programRule.project
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      programRule.createdBy,
      programRule.lastUpdatedBy
    );
    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing<IProgram>(
      this.programsSharedCollection,
      programRule.program
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.programRule?.project))
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.programRule?.createdBy,
            this.programRule?.lastUpdatedBy
          )
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) => this.programService.addProgramToCollectionIfMissing<IProgram>(programs, this.programRule?.program))
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));
  }
}
