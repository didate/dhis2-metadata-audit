import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProgramRuleActionFormService, ProgramRuleActionFormGroup } from './program-rule-action-form.service';
import { IProgramRuleAction } from '../program-rule-action.model';
import { ProgramRuleActionService } from '../service/program-rule-action.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgramRule } from 'app/entities/program-rule/program-rule.model';
import { ProgramRuleService } from 'app/entities/program-rule/service/program-rule.service';
import { ITrackedEntityAttribute } from 'app/entities/tracked-entity-attribute/tracked-entity-attribute.model';
import { TrackedEntityAttributeService } from 'app/entities/tracked-entity-attribute/service/tracked-entity-attribute.service';
import { IOptionGroup } from 'app/entities/option-group/option-group.model';
import { OptionGroupService } from 'app/entities/option-group/service/option-group.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-program-rule-action-update',
  templateUrl: './program-rule-action-update.component.html',
})
export class ProgramRuleActionUpdateComponent implements OnInit {
  isSaving = false;
  programRuleAction: IProgramRuleAction | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  programRulesSharedCollection: IProgramRule[] = [];
  trackedEntityAttributesSharedCollection: ITrackedEntityAttribute[] = [];
  optionGroupsSharedCollection: IOptionGroup[] = [];

  editForm: ProgramRuleActionFormGroup = this.programRuleActionFormService.createProgramRuleActionFormGroup();

  constructor(
    protected programRuleActionService: ProgramRuleActionService,
    protected programRuleActionFormService: ProgramRuleActionFormService,
    protected projectService: ProjectService,
    protected dHISUserService: DHISUserService,
    protected programRuleService: ProgramRuleService,
    protected trackedEntityAttributeService: TrackedEntityAttributeService,
    protected optionGroupService: OptionGroupService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareProgramRule = (o1: IProgramRule | null, o2: IProgramRule | null): boolean => this.programRuleService.compareProgramRule(o1, o2);

  compareTrackedEntityAttribute = (o1: ITrackedEntityAttribute | null, o2: ITrackedEntityAttribute | null): boolean =>
    this.trackedEntityAttributeService.compareTrackedEntityAttribute(o1, o2);

  compareOptionGroup = (o1: IOptionGroup | null, o2: IOptionGroup | null): boolean => this.optionGroupService.compareOptionGroup(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programRuleAction }) => {
      this.programRuleAction = programRuleAction;
      if (programRuleAction) {
        this.updateForm(programRuleAction);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programRuleAction = this.programRuleActionFormService.getProgramRuleAction(this.editForm);
    if (programRuleAction.id !== null) {
      this.subscribeToSaveResponse(this.programRuleActionService.update(programRuleAction));
    } else {
      this.subscribeToSaveResponse(this.programRuleActionService.create(programRuleAction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramRuleAction>>): void {
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

  protected updateForm(programRuleAction: IProgramRuleAction): void {
    this.programRuleAction = programRuleAction;
    this.programRuleActionFormService.resetForm(this.editForm, programRuleAction);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      programRuleAction.project
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      programRuleAction.createdBy,
      programRuleAction.lastUpdatedBy
    );
    this.programRulesSharedCollection = this.programRuleService.addProgramRuleToCollectionIfMissing<IProgramRule>(
      this.programRulesSharedCollection,
      programRuleAction.programRule
    );
    this.trackedEntityAttributesSharedCollection =
      this.trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing<ITrackedEntityAttribute>(
        this.trackedEntityAttributesSharedCollection,
        programRuleAction.trackedEntityAttribute,
        programRuleAction.dataElement
      );
    this.optionGroupsSharedCollection = this.optionGroupService.addOptionGroupToCollectionIfMissing<IOptionGroup>(
      this.optionGroupsSharedCollection,
      programRuleAction.optionGroup
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.programRuleAction?.project)
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
            this.programRuleAction?.createdBy,
            this.programRuleAction?.lastUpdatedBy
          )
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.programRuleService
      .query()
      .pipe(map((res: HttpResponse<IProgramRule[]>) => res.body ?? []))
      .pipe(
        map((programRules: IProgramRule[]) =>
          this.programRuleService.addProgramRuleToCollectionIfMissing<IProgramRule>(programRules, this.programRuleAction?.programRule)
        )
      )
      .subscribe((programRules: IProgramRule[]) => (this.programRulesSharedCollection = programRules));

    this.trackedEntityAttributeService
      .query()
      .pipe(map((res: HttpResponse<ITrackedEntityAttribute[]>) => res.body ?? []))
      .pipe(
        map((trackedEntityAttributes: ITrackedEntityAttribute[]) =>
          this.trackedEntityAttributeService.addTrackedEntityAttributeToCollectionIfMissing<ITrackedEntityAttribute>(
            trackedEntityAttributes,
            this.programRuleAction?.trackedEntityAttribute,
            this.programRuleAction?.dataElement
          )
        )
      )
      .subscribe(
        (trackedEntityAttributes: ITrackedEntityAttribute[]) => (this.trackedEntityAttributesSharedCollection = trackedEntityAttributes)
      );

    this.optionGroupService
      .query()
      .pipe(map((res: HttpResponse<IOptionGroup[]>) => res.body ?? []))
      .pipe(
        map((optionGroups: IOptionGroup[]) =>
          this.optionGroupService.addOptionGroupToCollectionIfMissing<IOptionGroup>(optionGroups, this.programRuleAction?.optionGroup)
        )
      )
      .subscribe((optionGroups: IOptionGroup[]) => (this.optionGroupsSharedCollection = optionGroups));
  }
}
