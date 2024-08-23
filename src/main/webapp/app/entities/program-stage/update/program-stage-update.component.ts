import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProgramStageFormService, ProgramStageFormGroup } from './program-stage-form.service';
import { IProgramStage } from '../program-stage.model';
import { ProgramStageService } from '../service/program-stage.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { IDataelement } from 'app/entities/dataelement/dataelement.model';
import { DataelementService } from 'app/entities/dataelement/service/dataelement.service';

@Component({
  selector: 'jhi-program-stage-update',
  templateUrl: './program-stage-update.component.html',
})
export class ProgramStageUpdateComponent implements OnInit {
  isSaving = false;
  programStage: IProgramStage | null = null;

  dHISUsersSharedCollection: IDHISUser[] = [];
  programsSharedCollection: IProgram[] = [];
  dataelementsSharedCollection: IDataelement[] = [];

  editForm: ProgramStageFormGroup = this.programStageFormService.createProgramStageFormGroup();

  constructor(
    protected programStageService: ProgramStageService,
    protected programStageFormService: ProgramStageFormService,
    protected dHISUserService: DHISUserService,
    protected programService: ProgramService,
    protected dataelementService: DataelementService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareProgram = (o1: IProgram | null, o2: IProgram | null): boolean => this.programService.compareProgram(o1, o2);

  compareDataelement = (o1: IDataelement | null, o2: IDataelement | null): boolean => this.dataelementService.compareDataelement(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programStage }) => {
      this.programStage = programStage;
      if (programStage) {
        this.updateForm(programStage);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programStage = this.programStageFormService.getProgramStage(this.editForm);
    if (programStage.id !== null) {
      this.subscribeToSaveResponse(this.programStageService.update(programStage));
    } else {
      this.subscribeToSaveResponse(this.programStageService.create(programStage));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramStage>>): void {
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

  protected updateForm(programStage: IProgramStage): void {
    this.programStage = programStage;
    this.programStageFormService.resetForm(this.editForm, programStage);

    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      programStage.createdBy,
      programStage.lastUpdatedBy
    );
    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing<IProgram>(
      this.programsSharedCollection,
      programStage.program
    );
    this.dataelementsSharedCollection = this.dataelementService.addDataelementToCollectionIfMissing<IDataelement>(
      this.dataelementsSharedCollection,
      ...(programStage.programStageDataElements ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dHISUserService
      .query()
      .pipe(map((res: HttpResponse<IDHISUser[]>) => res.body ?? []))
      .pipe(
        map((dHISUsers: IDHISUser[]) =>
          this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
            dHISUsers,
            this.programStage?.createdBy,
            this.programStage?.lastUpdatedBy
          )
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) => this.programService.addProgramToCollectionIfMissing<IProgram>(programs, this.programStage?.program))
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));

    this.dataelementService
      .query()
      .pipe(map((res: HttpResponse<IDataelement[]>) => res.body ?? []))
      .pipe(
        map((dataelements: IDataelement[]) =>
          this.dataelementService.addDataelementToCollectionIfMissing<IDataelement>(
            dataelements,
            ...(this.programStage?.programStageDataElements ?? [])
          )
        )
      )
      .subscribe((dataelements: IDataelement[]) => (this.dataelementsSharedCollection = dataelements));
  }
}
