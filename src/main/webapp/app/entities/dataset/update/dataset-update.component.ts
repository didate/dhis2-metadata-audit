import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DatasetFormService, DatasetFormGroup } from './dataset-form.service';
import { IDataset } from '../dataset.model';
import { DatasetService } from '../service/dataset.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { ICategorycombo } from 'app/entities/categorycombo/categorycombo.model';
import { CategorycomboService } from 'app/entities/categorycombo/service/categorycombo.service';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';
import { IIndicator } from 'app/entities/indicator/indicator.model';
import { IndicatorService } from 'app/entities/indicator/service/indicator.service';
import { IOrganisationUnit } from 'app/entities/organisation-unit/organisation-unit.model';
import { OrganisationUnitService } from 'app/entities/organisation-unit/service/organisation-unit.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-dataset-update',
  templateUrl: './dataset-update.component.html',
})
export class DatasetUpdateComponent implements OnInit {
  isSaving = false;
  dataset: IDataset | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  projectsSharedCollection: IProject[] = [];
  dHISUsersSharedCollection: IDHISUser[] = [];
  categorycombosSharedCollection: ICategorycombo[] = [];
  dataelementsSharedCollection: IDataelement[] = [];
  indicatorsSharedCollection: IIndicator[] = [];
  organisationUnitsSharedCollection: IOrganisationUnit[] = [];

  editForm: DatasetFormGroup = this.datasetFormService.createDatasetFormGroup();

  constructor(
    protected datasetService: DatasetService,
    protected datasetFormService: DatasetFormService,
    protected projectService: ProjectService,
    protected dHISUserService: DHISUserService,
    protected categorycomboService: CategorycomboService,
    protected dataelementService: DataelementService,
    protected indicatorService: IndicatorService,
    protected organisationUnitService: OrganisationUnitService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareCategorycombo = (o1: ICategorycombo | null, o2: ICategorycombo | null): boolean =>
    this.categorycomboService.compareCategorycombo(o1, o2);

  compareDataelement = (o1: IDataelement | null, o2: IDataelement | null): boolean => this.dataelementService.compareDataelement(o1, o2);

  compareIndicator = (o1: IIndicator | null, o2: IIndicator | null): boolean => this.indicatorService.compareIndicator(o1, o2);

  compareOrganisationUnit = (o1: IOrganisationUnit | null, o2: IOrganisationUnit | null): boolean =>
    this.organisationUnitService.compareOrganisationUnit(o1, o2);

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
      dataset.project
    );
    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      dataset.createdBy,
      dataset.lastUpdatedBy
    );
    this.categorycombosSharedCollection = this.categorycomboService.addCategorycomboToCollectionIfMissing<ICategorycombo>(
      this.categorycombosSharedCollection,
      dataset.categoryCombo
    );
    this.dataelementsSharedCollection = this.dataelementService.addDataelementToCollectionIfMissing<IDataelement>(
      this.dataelementsSharedCollection,
      ...(dataset.dataSetElements ?? [])
    );
    this.indicatorsSharedCollection = this.indicatorService.addIndicatorToCollectionIfMissing<IIndicator>(
      this.indicatorsSharedCollection,
      ...(dataset.indicators ?? [])
    );
    this.organisationUnitsSharedCollection = this.organisationUnitService.addOrganisationUnitToCollectionIfMissing<IOrganisationUnit>(
      this.organisationUnitsSharedCollection,
      ...(dataset.organisationUnits ?? [])
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
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(dHISUsers, this.dataset?.createdBy, this.dataset?.lastUpdatedBy)
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.categorycomboService
      .query()
      .pipe(map((res: HttpResponse<ICategorycombo[]>) => res.body ?? []))
      .pipe(
        map((categorycombos: ICategorycombo[]) =>
          this.categorycomboService.addCategorycomboToCollectionIfMissing<ICategorycombo>(categorycombos, this.dataset?.categoryCombo)
        )
      )
      .subscribe((categorycombos: ICategorycombo[]) => (this.categorycombosSharedCollection = categorycombos));

    this.dataelementService
      .query()
      .pipe(map((res: HttpResponse<IDataelement[]>) => res.body ?? []))
      .pipe(
        map((dataelements: IDataelement[]) =>
          this.dataelementService.addDataelementToCollectionIfMissing<IDataelement>(dataelements, ...(this.dataset?.dataSetElements ?? []))
        )
      )
      .subscribe((dataelements: IDataelement[]) => (this.dataelementsSharedCollection = dataelements));

    this.indicatorService
      .query()
      .pipe(map((res: HttpResponse<IIndicator[]>) => res.body ?? []))
      .pipe(
        map((indicators: IIndicator[]) =>
          this.indicatorService.addIndicatorToCollectionIfMissing<IIndicator>(indicators, ...(this.dataset?.indicators ?? []))
        )
      )
      .subscribe((indicators: IIndicator[]) => (this.indicatorsSharedCollection = indicators));

    this.organisationUnitService
      .query()
      .pipe(map((res: HttpResponse<IOrganisationUnit[]>) => res.body ?? []))
      .pipe(
        map((organisationUnits: IOrganisationUnit[]) =>
          this.organisationUnitService.addOrganisationUnitToCollectionIfMissing<IOrganisationUnit>(
            organisationUnits,
            ...(this.dataset?.organisationUnits ?? [])
          )
        )
      )
      .subscribe((organisationUnits: IOrganisationUnit[]) => (this.organisationUnitsSharedCollection = organisationUnits));
  }
}
