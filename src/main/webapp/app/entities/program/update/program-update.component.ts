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
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { CategorycomboService } from 'app/entities/categorycombo/service/categorycombo.service';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from 'app/entities/tracked-entity-attribute/service/tracked-entity-attribute.service';
import { IOrganisationUnit } from 'app/entities/organisation-unit/organisation-unit.model';
import { OrganisationUnitService } from 'app/entities/organisation-unit/service/organisation-unit.service';
import { IProgramIndicator } from 'app/entities/program-indicator/program-indicator.model';
import { ProgramIndicatorService } from 'app/entities/program-indicator/service/program-indicator.service';
import { IProgramStage } from 'app/entities/program-stage/program-stage.model';
import { ProgramStageService } from 'app/entities/program-stage/service/program-stage.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';
import { ProgramService } from '../service/program.service';
import { IProgram } from '../program.model';
import { ProgramFormService, ProgramFormGroup } from './program-form.service';

@Component({
  standalone: true,
  selector: 'jhi-program-update',
  templateUrl: './program-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProgramUpdateComponent implements OnInit {
  isSaving = false;
  program: IProgram | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  categorycombosSharedCollection: ICategorycombo[] = [];
  trackedEntityAttributesSharedCollection: ITrackedEntityAttribute[] = [];
  organisationUnitsSharedCollection: IOrganisationUnit[] = [];
  programIndicatorsSharedCollection: IProgramIndicator[] = [];
  programStagesSharedCollection: IProgramStage[] = [];

  protected programService = inject(ProgramService);
  protected programFormService = inject(ProgramFormService);
  protected projectService = inject(ProjectService);
  protected dHISUserService = inject(DHISUserService);
  protected categorycomboService = inject(CategorycomboService);
  protected trackedEntityAttributeService = inject(TrackedEntityAttributeService);
  protected organisationUnitService = inject(OrganisationUnitService);
  protected programIndicatorService = inject(ProgramIndicatorService);
  protected programStageService = inject(ProgramStageService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ProgramFormGroup = this.programFormService.createProgramFormGroup();

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareCategorycombo = (o1: ICategorycombo | null, o2: ICategorycombo | null): boolean =>
    this.categorycomboService.compareCategorycombo(o1, o2);

  compareTrackedEntityAttribute = (o1: ITrackedEntityAttribute | null, o2: ITrackedEntityAttribute | null): boolean =>
    this.trackedEntityAttributeService.compareTrackedEntityAttribute(o1, o2);

  compareOrganisationUnit = (o1: IOrganisationUnit | null, o2: IOrganisationUnit | null): boolean =>
    this.organisationUnitService.compareOrganisationUnit(o1, o2);

  compareProgramIndicator = (o1: IProgramIndicator | null, o2: IProgramIndicator | null): boolean =>
    this.programIndicatorService.compareProgramIndicator(o1, o2);

  compareProgramStage = (o1: IProgramStage | null, o2: IProgramStage | null): boolean =>
    this.programStageService.compareProgramStage(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ program }) => {
      this.program = program;
      if (program) {
        this.updateForm(program);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const program = this.programFormService.getProgram(this.editForm);
    if (program.id !== null) {
      this.subscribeToSaveResponse(this.programService.update(program));
    } else {
      this.subscribeToSaveResponse(this.programService.create(program));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgram>>): void {
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

  protected updateForm(program: IProgram): void {
    this.program = program;
    this.programFormService.resetForm(this.editForm, program);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      program.project,
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      program.createdBy,
      program.lastUpdatedBy,
    );
    this.categorycombosSharedCollection = this.categorycomboService.addCategorycomboToCollectionIfMissing<ICategorycombo>(
      this.categorycombosSharedCollection,
      program.categoryCombo,
    );
    this.trackedEntityAttributesSharedCollection =
      this.trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing<ITrackedEntityAttribute>(
        this.trackedEntityAttributesSharedCollection,
        ...(program.programTrackedEntityAttributes ?? []),
      );
    this.organisationUnitsSharedCollection = this.organisationUnitService.addOrganisationUnitToCollectionIfMissing<IOrganisationUnit>(
      this.organisationUnitsSharedCollection,
      ...(program.organisationUnits ?? []),
    );
    this.programIndicatorsSharedCollection = this.programIndicatorService.addProgramIndicatorToCollectionIfMissing<IProgramIndicator>(
      this.programIndicatorsSharedCollection,
      ...(program.programIndicators ?? []),
    );
    this.programStagesSharedCollection = this.programStageService.addProgramStageToCollectionIfMissing<IProgramStage>(
      this.programStagesSharedCollection,
      ...(program.programStages ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.program?.project)))
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(dHISUsers, this.program?.createdBy, this.program?.lastUpdatedBy),
        ),
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.categorycomboService
      .query()
      .pipe(map((res: HttpResponse<ICategorycombo[]>) => res.body ?? []))
      .pipe(
        map((categorycombos: ICategorycombo[]) =>
          this.categorycomboService.addCategorycomboToCollectionIfMissing<ICategorycombo>(categorycombos, this.program?.categoryCombo),
        ),
      )
      .subscribe((categorycombos: ICategorycombo[]) => (this.categorycombosSharedCollection = categorycombos));

    this.trackedEntityAttributeService
      .query()
      .pipe(map((res: HttpResponse<ITrackedEntityAttribute[]>) => res.body ?? []))
      .pipe(
        map((trackedEntityAttributes: ITrackedEntityAttribute[]) =>
          this.trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing<ITrackedEntityAttribute>(
            trackedEntityAttributes,
            ...(this.program?.programTrackedEntityAttributes ?? []),
          ),
        ),
      )
      .subscribe(
        (trackedEntityAttributes: ITrackedEntityAttribute[]) => (this.trackedEntityAttributesSharedCollection = trackedEntityAttributes),
      );

    this.organisationUnitService
      .query()
      .pipe(map((res: HttpResponse<IOrganisationUnit[]>) => res.body ?? []))
      .pipe(
        map((organisationUnits: IOrganisationUnit[]) =>
          this.organisationUnitService.addOrganisationUnitToCollectionIfMissing<IOrganisationUnit>(
            organisationUnits,
            ...(this.program?.organisationUnits ?? []),
          ),
        ),
      )
      .subscribe((organisationUnits: IOrganisationUnit[]) => (this.organisationUnitsSharedCollection = organisationUnits));

    this.programIndicatorService
      .query()
      .pipe(map((res: HttpResponse<IProgramIndicator[]>) => res.body ?? []))
      .pipe(
        map((programIndicators: IProgramIndicator[]) =>
          this.programIndicatorService.addProgramIndicatorToCollectionIfMissing<IProgramIndicator>(
            programIndicators,
            ...(this.program?.programIndicators ?? []),
          ),
        ),
      )
      .subscribe((programIndicators: IProgramIndicator[]) => (this.programIndicatorsSharedCollection = programIndicators));

    this.programStageService
      .query()
      .pipe(map((res: HttpResponse<IProgramStage[]>) => res.body ?? []))
      .pipe(
        map((programStages: IProgramStage[]) =>
          this.programStageService.addProgramStageToCollectionIfMissing<IProgramStage>(
            programStages,
            ...(this.program?.programStages ?? []),
          ),
        ),
      )
      .subscribe((programStages: IProgramStage[]) => (this.programStagesSharedCollection = programStages));
  }
}
