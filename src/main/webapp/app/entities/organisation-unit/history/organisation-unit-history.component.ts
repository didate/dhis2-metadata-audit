import { Component, OnInit } from '@angular/core';
import { IOrganisationUnit } from '../organisation-unit.model';
import { EntityArrayResponseType, OrganisationUnitService } from '../service/organisation-unit.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-organisation-unit-history',
  templateUrl: './organisation-unit-history.component.html',
  styleUrls: ['./organisation-unit-history.component.scss'],
})
export class OrganisationUnitHistoryComponent implements OnInit {
  organisationUnit: IOrganisationUnit | null = null;
  organisationUnits?: IOrganisationUnit[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected organisationUnitService: OrganisationUnitService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IOrganisationUnit): string => this.organisationUnitService.getOrganisationUnitIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisationUnit }) => {
      this.organisationUnit = organisationUnit;
      this.load();
    });
  }

  load(): void {
    this.organisationUnitService
      .history(this.organisationUnit?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.organisationUnits = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(organisationUnit: IOrganisationUnit) {
    if (this.setRev.length < 2 && organisationUnit.isSelected) {
      this.setRev.push(organisationUnit.revisionNumber as number);
    } else if (!organisationUnit.isSelected) {
      const index = this.setRev.indexOf(organisationUnit.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      organisationUnit.isSelected = false;
    }
  }
}
