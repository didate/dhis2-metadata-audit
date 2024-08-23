import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPersonNotifier } from '../person-notifier.model';

@Component({
  selector: 'jhi-person-notifier-detail',
  templateUrl: './person-notifier-detail.component.html',
})
export class PersonNotifierDetailComponent implements OnInit {
  personNotifier: IPersonNotifier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ personNotifier }) => {
      this.personNotifier = personNotifier;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
