import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProgramIndicatorFormService, ProgramIndicatorFormGroup } from './program-indicator-form.service';
import { IProgramIndicator } from '../program-indicator.model';
import { ProgramIndicatorService } from '../service/program-indicator.service';
import { IDHISUser } from 'app/entities/dhis-user/dhis-user.model';
import { DHISUserService } from 'app/entities/dhis-user/service/dhis-user.service';
import { IProgram } from 'app/entities/program/program.model';
import { ProgramService } from 'app/entities/program/service/program.service';
import { TypeTrack } from 'app/entities/enumerations/type-track.model';

@Component({
  selector: 'jhi-program-indicator-update',
  templateUrl: './program-indicator-update.component.html',
})
export class ProgramIndicatorUpdateComponent implements OnInit {
  isSaving = false;
  programIndicator: IProgramIndicator | null = null;
  typeTrackValues = Object.keys(TypeTrack);

  dHISUsersSharedCollection: IDHISUser[] = [];
  programsSharedCollection: IProgram[] = [];

  editForm: ProgramIndicatorFormGroup = this.programIndicatorFormService.createProgramIndicatorFormGroup();

  constructor(
    protected programIndicatorService: ProgramIndicatorService,
    protected programIndicatorFormService: ProgramIndicatorFormService,
    protected dHISUserService: DHISUserService,
    protected programService: ProgramService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDHISUser = (o1: IDHISUser | null, o2: IDHISUser | null): boolean => this.dHISUserService.compareDHISUser(o1, o2);

  compareProgram = (o1: IProgram | null, o2: IProgram | null): boolean => this.programService.compareProgram(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programIndicator }) => {
      this.programIndicator = programIndicator;
      if (programIndicator) {
        this.updateForm(programIndicator);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programIndicator = this.programIndicatorFormService.getProgramIndicator(this.editForm);
    if (programIndicator.id !== null) {
      this.subscribeToSaveResponse(this.programIndicatorService.update(programIndicator));
    } else {
      this.subscribeToSaveResponse(this.programIndicatorService.create(programIndicator));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramIndicator>>): void {
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

  protected updateForm(programIndicator: IProgramIndicator): void {
    this.programIndicator = programIndicator;
    this.programIndicatorFormService.resetForm(this.editForm, programIndicator);

    this.dHISUsersSharedCollection = this.dHISUserService.addDHISUserToCollectionIfMissing<IDHISUser>(
      this.dHISUsersSharedCollection,
      programIndicator.createdBy,
      programIndicator.lastUpdatedBy
    );
    this.programsSharedCollection = this.programService.addProgramToCollectionIfMissing<IProgram>(
      this.programsSharedCollection,
      programIndicator.program
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
            this.programIndicator?.createdBy,
            this.programIndicator?.lastUpdatedBy
          )
        )
      )
      .subscribe((dHISUsers: IDHISUser[]) => (this.dHISUsersSharedCollection = dHISUsers));

    this.programService
      .query()
      .pipe(map((res: HttpResponse<IProgram[]>) => res.body ?? []))
      .pipe(
        map((programs: IProgram[]) =>
          this.programService.addProgramToCollectionIfMissing<IProgram>(programs, this.programIndicator?.program)
        )
      )
      .subscribe((programs: IProgram[]) => (this.programsSharedCollection = programs));
  }
}
