import { Component, OnInit } from '@angular/core';
import { IDHISUser } from '../dhis-user.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { DHISUserService, EntityArrayResponseType } from '../service/dhis-user.service';

@Component({
  selector: 'jhi-dhis-user-history',
  templateUrl: './dhis-user-history.component.html',
  styleUrls: ['./dhis-user-history.component.scss'],
})
export class DhisUserHistoryComponent implements OnInit {
  dHISUser: IDHISUser | null = null;
  dHISUsers?: IDHISUser[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected dHisService: DHISUserService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IDHISUser): string => this.dHisService.getDHISUserIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dHISUser }) => {
      this.dHISUser = dHISUser;
      this.load();
    });
  }

  load(): void {
    this.dHisService
      .history(this.dHISUser?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.dHISUsers = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(dHis: IDHISUser) {
    if (this.setRev.length < 2 && dHis.isSelected) {
      this.setRev.push(dHis.revisionNumber as number);
    } else if (!dHis.isSelected) {
      const index = this.setRev.indexOf(dHis.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      dHis.isSelected = false;
    }
  }
}
