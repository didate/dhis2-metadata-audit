import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProgramRuleVariableFormService, ProgramRuleVariableFormGroup } from './program-rule-variable-form.service';
import { IProgramRuleVariable } from '../program-rule-variable.model';
import { ProgramRuleVariableService } from '../service/program-rule-variable.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from 'app/entities/tracked-entity-attribute/service/tracked-entity-attribute.service';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-program-rule-variable-update',
  templateUrl: './program-rule-variable-update.component.html',
})
export class ProgramRuleVariableUpdateComponent implements OnInit {
  isSaving = false;
  programRuleVariable: IProgramRuleVariable | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  programsSharedCollection: IProgram[] = [];
  trackedEntityAttributesSharedCollection: ITrackedEntityAttribute[] = [];
  dataelementsSharedCollection: IDataelement[] = [];

  editForm: ProgramRuleVariableFormGroup = this.programRuleVariableFormService.createProgramRuleVariableFormGroup();

  constructor(
    protected programRuleVariableService: ProgramRuleVariableService,
    protected programRuleVariableFormService: ProgramRuleVariableFormService,
    protected projectService: ProjectService,
    protected dHISUserService: DHISUserService,
    protected programService: ProgramService,
    protected trackedEntityAttributeService: TrackedEntityAttributeService,
    protected dataelementService: DataelementService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareProgram = (o1: IProgram | null, o2: IProgram | null): boolean => this.programService.compareProgram(o1, o2);

  compareTrackedEntityAttribute = (o1: ITrackedEntityAttribute | null, o2: ITrackedEntityAttribute | null): boolean =>
    this.trackedEntityAttributeService.compareTrackedEntityAttribute(o1, o2);

  compareDataelement = (o1: IDataelement | null, o2: IDataelement | null): boolean => this.dataelementService.compareDataelement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRuleVariable }) => {
      this.programRuleVariable = programRuleVariable;
      if (programRuleVariable) {
        this.updateForm(programRuleVariable);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programRuleVariable = this.programRuleVariableFormService.getProgramRuleVariable(this.editForm);
    if (programRuleVariable.id !== null) {
      this.subscribeToSaveResponse(this.programRuleVariableService.update(programRuleVariable));
    } else {
      this.subscribeToSaveResponse(this.programRuleVariableService.create(programRuleVariable));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramRuleVariable>>): void {
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

  protected updateForm(programRuleVariable: IProgramRuleVariable): void {
    this.programRuleVariable = programRuleVariable;
    this.programRuleVariableFormService.resetForm(this.editForm, programRuleVariable);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      programRuleVariable.project
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      programRuleVariable.createdBy,
      programRuleVariable.lastUpdatedBy
    );
    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing<IProgram>(
      this.programsSharedCollection,
      programRuleVariable.program
    );
    this.trackedEntityAttributesSharedCollection =
      this.trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing<ITrackedEntityAttribute>(
        this.trackedEntityAttributesSharedCollection,
        programRuleVariable.trackedEntityAttribute
      );
    this.dataelementsSharedCollection = this.dataelementService.addDataelementToCollectionIfMissing<IDataelement>(
      this.dataelementsSharedCollection,
      programRuleVariable.dataElement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.programRuleVariable?.project)
        )
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.programRuleVariable?.createdBy,
            this.programRuleVariable?.lastUpdatedBy
          )
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) =>
          this.programService.addProgramToCollectionIfMissing<IProgram>(programs, this.programRuleVariable?.program)
        )
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));

    this.trackedEntityAttributeService
      .query()
      .pipe(map((res: HttpResponse<ITrackedEntityAttribute[]>) => res.body ?? []))
      .pipe(
        map((trackedEntityAttributes: ITrackedEntityAttribute[]) =>
          this.trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing<ITrackedEntityAttribute>(
            trackedEntityAttributes,
            this.programRuleVariable?.trackedEntityAttribute
          )
        )
      )
      .subscribe(
        (trackedEntityAttributes: ITrackedEntityAttribute[]) => (this.trackedEntityAttributesSharedCollection = trackedEntityAttributes)
      );

    this.dataelementService
      .query()
      .pipe(map((res: HttpResponse<IDataelement[]>) => res.body ?? []))
      .pipe(
        map((dataelements: IDataelement[]) =>
          this.dataelementService.addDataelementToCollectionIfMissing<IDataelement>(dataelements, this.programRuleVariable?.dataElement)
        )
      )
      .subscribe((dataelements: IDataelement[]) => (this.dataelementsSharedCollection = dataelements));
  }
}
