import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganisationUnit } from '../organisation-unit.model';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';
import { OrganisationUnitService, EntityArrayResponseType } from '../service/organisation-unit.service';

@Component({
  selector: 'jhi-organisation-unit-detail',
  templateUrl: './organisation-unit-detail.component.html',
})
export class OrganisationUnitDetailComponent implements OnInit {
  organisationUnit: IOrganisationUnit | null = null;
  organisationUnitRev1: IOrganisationUnit | null = null;
  organisationUnitRev2: IOrganisationUnit | null = null;

  organisationUnits?: IOrganisationUnit[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected organisationUnitService: OrganisationUnitService,
    private diffService: DiffService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ organisationUnit }) => {
        this.organisationUnit = organisationUnit;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.organisationUnit) {
      this.organisationUnitService
        .compare(this.organisationUnit.id, rev1, rev2)
        .pipe(tap(() => (this.isLoading = false)))
        .subscribe({
          next: (res: EntityArrayResponseType) => {
            this.onResponseSuccess(res);
          },
        });
    }
  }

  showDiff(text1: any, text2: any): any {
    if (text1) {
      return this.diffService.generateDiff(text1 as string, text2 as string);
    }
    return text1;
  }

  previousState(): void {
    window.history.back();
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.organisationUnits = dataFromBody;
    this.organisationUnitRev1 = this.organisationUnits[0];
    this.organisationUnitRev2 = this.organisationUnits[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IOrganisationUnit[] | null): IOrganisationUnit[] {
    return data ?? [];
  }
}
