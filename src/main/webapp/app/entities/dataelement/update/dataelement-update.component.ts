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
import { IOptionset } from 'app/entities/optionset/optionset.model';
import { OptionsetService } from 'app/entities/optionset/service/optionset.service';
import { DataelementService } from '../service/dataelement.service';
import { IDataelement } from '../dataelement.model';
import { DataelementFormService, DataelementFormGroup } from './dataelement-form.service';

@Component({
  standalone: true,
  selector: 'jhi-dataelement-update',
  templateUrl: './dataelement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DataelementUpdateComponent implements OnInit {
  isSaving = false;
  dataelement: IDataelement | null = null;

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  categorycombosSharedCollection: ICategorycombo[] = [];
  optionsetsSharedCollection: IOptionset[] = [];

  protected dataelementService = inject(DataelementService);
  protected dataelementFormService = inject(DataelementFormService);
  protected projectService = inject(ProjectService);
  protected dHISUserService = inject(DHISUserService);
  protected categorycomboService = inject(CategorycomboService);
  protected optionsetService = inject(OptionsetService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DataelementFormGroup = this.dataelementFormService.createDataelementFormGroup();

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareCategorycombo = (o1: ICategorycombo | null, o2: ICategorycombo | null): boolean =>
    this.categorycomboService.compareCategorycombo(o1, o2);

  compareOptionset = (o1: IOptionset | null, o2: IOptionset | null): boolean => this.optionsetService.compareOptionset(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataelement }) => {
      this.dataelement = dataelement;
      if (dataelement) {
        this.updateForm(dataelement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataelement = this.dataelementFormService.getDataelement(this.editForm);
    if (dataelement.id !== null) {
      this.subscribeToSaveResponse(this.dataelementService.update(dataelement));
    } else {
      this.subscribeToSaveResponse(this.dataelementService.create(dataelement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataelement>>): void {
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

  protected updateForm(dataelement: IDataelement): void {
    this.dataelement = dataelement;
    this.dataelementFormService.resetForm(this.editForm, dataelement);

    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      dataelement.project,
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      dataelement.createdBy,
      dataelement.lastUpdatedBy,
    );
    this.categorycombosSharedCollection = this.categorycomboService.addCategorycomboToCollectionIfMissing<ICategorycombo>(
      this.categorycombosSharedCollection,
      dataelement.categoryCombo,
    );
    this.optionsetsSharedCollection = this.optionsetService.addOptionsetToCollectionIfMissing<IOptionset>(
      this.optionsetsSharedCollection,
      dataelement.optionSet,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing<IProject>(projects, this.dataelement?.project)),
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.dataelement?.createdBy,
            this.dataelement?.lastUpdatedBy,
          ),
        ),
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.categorycomboService
      .query()
      .pipe(map((res: HttpResponse<ICategorycombo[]>) => res.body ?? []))
      .pipe(
        map((categorycombos: ICategorycombo[]) =>
          this.categorycomboService.addCategorycomboToCollectionIfMissing<ICategorycombo>(categorycombos, this.dataelement?.categoryCombo),
        ),
      )
      .subscribe((categorycombos: ICategorycombo[]) => (this.categorycombosSharedCollection = categorycombos));

    this.optionsetService
      .query()
      .pipe(map((res: HttpResponse<IOptionset[]>) => res.body ?? []))
      .pipe(
        map((optionsets: IOptionset[]) =>
          this.optionsetService.addOptionsetToCollectionIfMissing<IOptionset>(optionsets, this.dataelement?.optionSet),
        ),
      )
      .subscribe((optionsets: IOptionset[]) => (this.optionsetsSharedCollection = optionsets));
  }
}
