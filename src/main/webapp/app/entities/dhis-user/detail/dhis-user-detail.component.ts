import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDHISUser } from '../dhis-user.model';

@Component({
  selector: 'jhi-dhis-user-detail',
  templateUrl: './dhis-user-detail.component.html',
})
export class DHISUserDetailComponent implements OnInit {
  dHISUser: IDHISUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dHISUser }) => {
      this.dHISUser = dHISUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
