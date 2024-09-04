import { Component, OnInit } from '@angular/core';
import { IOptionGroup } from '../option-group.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { tap } from 'rxjs';
import { OptionGroupService, EntityArrayResponseType } from '../service/option-group.service';

@Component({
  selector: 'jhi-option-group-history',
  templateUrl: './option-group-history.component.html',
  styleUrls: ['./option-group-history.component.scss'],
})
export class OptionGroupHistoryComponent implements OnInit {
  optionGroup: IOptionGroup | null = null;
  optionGroups?: IOptionGroup[];
  isLoading = false;

  setRev: number[] = [];

  constructor(
    protected optionGroupService: OptionGroupService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IOptionGroup): string => this.optionGroupService.getOptionGroupIdentifier(item);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ optionGroup }) => {
      this.optionGroup = optionGroup;
      this.load();
    });
  }

  load(): void {
    this.optionGroupService
      .history(this.optionGroup?.id)
      .pipe(tap(() => (this.isLoading = false)))
      .subscribe({
        next: (res: EntityArrayResponseType) => (this.optionGroups = res.body ?? []),
      });
  }

  previousState(): void {
    window.history.back();
  }

  selectRev(optionGroup: IOptionGroup) {
    if (this.setRev.length < 2 && optionGroup.isSelected) {
      this.setRev.push(optionGroup.revisionNumber as number);
    } else if (!optionGroup.isSelected) {
      const index = this.setRev.indexOf(optionGroup.revisionNumber as number);
      if (index > -1) {
        this.setRev.splice(index, 1);
      }
    } else {
      optionGroup.isSelected = false;
    }
  }
}
