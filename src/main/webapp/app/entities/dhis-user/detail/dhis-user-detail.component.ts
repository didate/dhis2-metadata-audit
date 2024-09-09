import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDHISUser } from '../dhis-user.model';
import { DHISUserService, EntityArrayResponseType } from '../service/dhis-user.service';
import { DiffService } from 'app/entities/diff.service';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-dhis-user-detail',
  templateUrl: './dhis-user-detail.component.html',
})
export class DHISUserDetailComponent implements OnInit {
  dHISUser: IDHISUser | null = null;

  dHISUserRev1: IDHISUser | null = null;
  dHISUserRev2: IDHISUser | null = null;

  dHISUsers?: IDHISUser[];
  isLoading = false;

  rev1 = 0;
  rev2 = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected dHISUserService: DHISUserService, private diffService: DiffService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.rev1 = params.get('rev1') as unknown as number;
      this.rev2 = params.get('rev2') as unknown as number;

      this.activatedRoute.data.subscribe(({ dHISUser }) => {
        this.dHISUser = dHISUser;
        this.load(this.rev1, this.rev2);
      });
    });
  }

  load(rev1: number, rev2: number): void {
    if (this.dHISUser) {
      this.dHISUserService
        .compare(this.dHISUser.id, rev1, rev2)
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
    this.dHISUsers = dataFromBody;
    this.dHISUserRev1 = this.dHISUsers[0];
    this.dHISUserRev2 = this.dHISUsers[1];
  }

  protected fillComponentAttributesFromResponseBody(data: IDHISUser[] | null): IDHISUser[] {
    return data ?? [];
  }
}
