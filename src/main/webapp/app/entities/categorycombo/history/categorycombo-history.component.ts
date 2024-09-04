import { Component, OnInit } from '@angular/core';
import { CategorycomboService, EntityArrayResponseType } from '../service/categorycombo.service';
import { ICategorycombo } from '../categorycombo.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';

@Component({
  selector: 'jhi-categorycombo-history',
  templateUrl: './categorycombo-history.component.html',
  styleUrls: ['./categorycombo-history.component.scss'],
})
export class CategorycomboHistoryComponent implements OnInit {
  categorycombo: ICategorycombo | null = null;
  categorycombos?: ICategorycombo[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected categorycomboService: CategorycomboService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: ICategorycombo): string => this.categorycomboService.getCategorycomboIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorycombo }) => {
      this.categorycombo = categorycombo;
      this.load();
    });
  }

  load(): void {
    this.categorycomboService
      .history(this.categorycombo?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.categorycombos = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(categorycombo: ICategorycombo) {
    if (this.setRev.length < 2 && categorycombo.isSelected) {
      this.setRev.push(categorycombo.revisionNumber as number);
    } else if (!categorycombo.isSelected) {
      const index = this.setRev.indexOf(categorycombo.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      categorycombo.isSelected = false;
    }
  }
}
